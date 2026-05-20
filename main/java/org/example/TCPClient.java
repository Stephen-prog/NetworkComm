package org.example;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        // try-with-resources: socket automatically closed when try block exits (Java 7+)
        // the constructor performs the TCP three-way handshake here
        try (Socket socket = new Socket("localhost", 1234)) {

            // create writer to send data; true = auto-flush, so println() sends immediately
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // create reader to receive data from server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // send message; println() adds newline - server's readLine() waits for this newline
            out.println("Hello from client!");

            // blocks waiting for server's response; returns when server sends line ending with newline
            String response = in.readLine();
            System.out.println("Server replied: " + response);

        } // <-- socket.close() called automatically here by try-with-resources
    }
}
