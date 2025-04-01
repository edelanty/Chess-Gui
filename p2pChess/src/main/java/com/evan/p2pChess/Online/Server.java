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
    }

    /**
     * getServerIPAddresses()
     * 
     * Grabs the IP of the user who started the server.
     * 
     * @return
     */
    public String getServerIPAddresses() {
        StringBuilder ipInfo = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (address instanceof Inet4Address) {
                            ipInfo.append(address.getHostAddress()).append("\n");
                        }
                    }
                }
            }
        } catch (SocketException e) {
            ipInfo.append("Error retrieving network addresses: ").append(e.getMessage());
        }
        
        return ipInfo.toString();
    }    

    public void waitForConnection() throws IOException {
        clientSocket = serverSocket.accept();
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendMove(String move) throws IOException {
        output.println(move);
    }

    public String receiveMove() throws IOException {
        return input.readLine();
    }
}
