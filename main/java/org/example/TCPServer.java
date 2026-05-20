package org.example;

import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        // to create serverSocket bound to port 1234 - tells OS to listen for TCP connections on this port
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Server started on port 1234");

        // infinite loop - keeps server running forever, handling clients one after another
        while (true) {
            // blocks until a client connects; returns a Socket object for that specific connection
            Socket client = server.accept();
            System.out.println("Client connected: " + client.getInetAddress());

            // wrap input stream: InputStreamReader converts bytes→characters, BufferedReader adds readLine()
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream())
            );
            // wrap output stream: true enables auto-flush - data sent immediately on println()
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            // blocks until client sends a full line (ending with newline); returns line without newline
            String msg = in.readLine();
            System.out.println("Received: " + msg);

            // send response back to client; println() adds newline which client's readLine() expects
            out.println("Echo: " + msg);

            // close this client's socket - frees resources for the next client
            client.close();
        }
    }
}