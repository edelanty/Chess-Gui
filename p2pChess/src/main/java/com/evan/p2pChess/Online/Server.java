package com.evan.p2pChess.Online;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class Server implements NetworkConnection {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        displayServerIP();
    }

    private void displayServerIP() {
        try {
            System.out.println("Server started. Your IP addresses:");
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (address instanceof Inet4Address) {
                            System.out.println(networkInterface.getDisplayName() + ": " + address.getHostAddress());
                        }
                    }
                }
            }
            System.out.println("Port: " + serverSocket.getLocalPort());
            System.out.println("Share this information with your friend to connect!");
        } catch (SocketException e) {
            System.err.println("Error retrieving network interfaces: " + e.getMessage());
        }
    }

    public void waitForConnection() throws IOException {
        System.out.println("Waiting for a connection...");
        clientSocket = serverSocket.accept();
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        System.out.println("Client connected from: " + clientSocket.getInetAddress().getHostAddress());
    }

    public void sendMove(String move) throws IOException {
        output.println(move);
    }

    public String receiveMove() throws IOException {
        return input.readLine();
    }
}
