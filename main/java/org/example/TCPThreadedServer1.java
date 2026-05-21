package org.example;

import java.io.*;
import java.net.*;

public class TCPThreadedServer1 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Threaded server started");

        while (true) {
            Socket client = server.accept();         // blocks until client connects
            new ClientHandler(client).start();       // hand off to dedicated thread
        }
    }

    // Each client gets its own handler — cleaner than a lambda
    private static class ClientHandler extends Thread {

        private Socket client;                       // this handler owns this socket

        public ClientHandler(Socket client) {
            this.client = client;                    // store the client for use in run()
        }

        @Override
        public void run() {                          // executes in its own thread
            try (
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(
                            client.getOutputStream(), true)  // true = auto-flush
            ) {
                String msg = in.readLine();          // wait for client message
                System.out.println("Received: " + msg);
                out.println("Echo: " + msg);         // send response
                client.close();                      // done — free resources
            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage()); // one client fails, others keep running
            }
        }
    }
}