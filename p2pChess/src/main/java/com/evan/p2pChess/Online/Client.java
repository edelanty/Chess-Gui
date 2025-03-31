package com.evan.p2pChess.Online;

import java.io.*;
import java.net.*;

public class Client implements NetworkConnection {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connected to server.");
    }

    public void sendMove(String move) throws IOException {
        output.println(move);
    }

    public String receiveMove() throws IOException {
        return input.readLine();
    }
}
