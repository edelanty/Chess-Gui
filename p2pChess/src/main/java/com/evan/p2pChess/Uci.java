package com.evan.p2pChess;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Intended to communicate to the Universal Chess Interface (UCI) to find and play the best move's in the Play Against AI mode.
 * 
 * @author Andrei Ciobanu - https://www.andreinc.net/2021/04/22/writing-a-universal-chess-interface-client-in-java
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/20/2025
 */
public class Uci {
    private Process process;
    private BufferedReader reader;
    private OutputStreamWriter writer;

    public Uci() {
        this.process = null;
        this.reader = null;
        this.writer = null;
    }

    /**
     * 
     * 
     * @param cmd
     * * @author Evan Delanty
     */
    public void start(String cmd) {
        ProcessBuilder pb;
        if (System.getProperty("os.name").toLowerCase().contains("win")) { //Windows
            String workingDir = System.getProperty("user.dir");
            String enginePath = workingDir + "/p2pChess/lib/stockfish/stockfish-windows-x86-64-avx2.exe";
            pb = new ProcessBuilder("cmd.exe", "/c", enginePath); 
        } else { //Linux
            pb = new ProcessBuilder(cmd);
        }
        
        try {
            this.process = pb.start();
            this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            this.writer = new OutputStreamWriter(process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * * @author Andrei Ciobanu
     */
    public void close() {
        if (this.process.isAlive()) {
            this.process.destroy();
        }
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * 
     * @param <T>
     * @param cmd
     * @param commandProcessor
     * @param breakCondition
     * @param timeout
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @author Andrei Ciobanu
     */
    public <T>T command(String cmd, Function<List<String>, T> commandProcessor, Predicate<String> breakCondition, long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        // This completable future will send a command to the process
        // And gather all the output of the engine in the List<String>
        // At the end, the List<String> is translated to T through the
        // commandProcessor Function
        CompletableFuture<T> command = supplyAsync(() -> {
            final List<String> output = new ArrayList<>();
            try {
                writer.flush();
                writer.write(cmd + "\n");
                writer.write("isready\n");
                writer.flush();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Unknown command")) {
                        throw new RuntimeException(line);
                    }
                    if (line.contains("Unexpected token")) {
                        throw new RuntimeException("Unexpected token: " + line);
                    }
                    output.add(line);
                    if (breakCondition.test(line)) {
                        // At this point we are no longer interested to read any more
                        // output from the engine, we consider that the engine responded
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return commandProcessor.apply(output);
        });

        return command.get(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * getBestMove()
     * 
     * Gets the best move given a FEN board state.
     * 
     * @param fen
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @author Evan Delanty
     */
    public String getBestMove(String fen) throws InterruptedException, ExecutionException, TimeoutException {
        command("position fen " + fen, Function.identity(), s -> s.startsWith("readyok"), 2000l);
        String bestMove = command(
            "go movetime 3000",
            lines -> lines.stream().filter(s->s.startsWith("bestmove")).findFirst().get(),
            line -> line.startsWith("bestmove"),
            5000l)
            .split(" ")[1];
        return bestMove;
    }

}
