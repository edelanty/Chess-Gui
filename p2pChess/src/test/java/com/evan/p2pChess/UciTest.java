package com.evan.p2pChess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class UciTest {
    @Test
    public void stockfishBestMoveTest() throws InterruptedException, ExecutionException, TimeoutException {
        Uci uci = new Uci();
        String bestMove = "";
        String expected = "f4g3";
        String position = "8/8/4Rp2/5P2/1PP1pkP1/7P/1P1r4/7K b - - 0 40";
        uci.start("stockfish");
        uci.command("uci", Function.identity(), (s) -> s.startsWith("uciok"), 2000l);
        // We set the give position
        uci.command("position fen " + position, Function.identity(), s -> s.startsWith("readyok"), 2000l);
        bestMove = uci.command(
            "go movetime 3000",
            lines -> lines.stream().filter(s->s.startsWith("bestmove")).findFirst().get(),
            line -> line.startsWith("bestmove"),
            5000l)
            .split(" ")[1];
        assertEquals(expected, bestMove);
        uci.close();
    }

    @Test
    public void stockfishBestMoveFunctionTest() throws InterruptedException, ExecutionException, TimeoutException {
        Uci uci = new Uci();
        String bestMove = "";
        String expected = "f4g3";
        String position = "8/8/4Rp2/5P2/1PP1pkP1/7P/1P1r4/7K b - - 0 40";
        uci.start("stockfish");
        bestMove = uci.getBestMove(position);
        assertEquals(expected, bestMove);
        uci.close();
    }

}
