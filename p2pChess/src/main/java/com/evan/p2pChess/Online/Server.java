package com.evan.p2pChess.Online;

import java.io.*;
import java.net.*;

public class Server implements NetworkConnection {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void waitForConnection() throws IOException {
        System.out.println("Waiting for a connection...");
        clientSocket = serverSocket.accept();
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        System.out.println("Client connected.");
    }

    public void sendMove(String move) throws IOException {
        output.println(move);
    }

    public String receiveMove() throws IOException {
        return input.readLine();
    }
}
