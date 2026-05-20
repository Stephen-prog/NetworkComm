package org.example;
import java.io.*;
import java.net.*;

public class TCPThreadedServer {
    public static void main(String[] args) throws IOException {
        // create server socket bound to port 1234
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Threaded server started on port 1234");

        // Infinite loop - keeps accepting clients forever
        while (true) {
            // BLOCKS until a client connects
            Socket client = server.accept();

            // create and start a new thread for this client (lambda: "when thread runs, call handleClient")
            // this allows multiple clients to be processed simultaneously
            new Thread(() -> handleClient(client)).start();
        }
    }

    // This method runs in its own thread for each client
    static void handleClient(Socket client) {
        try {
            // create reader to get text from this client
            BufferedReader in = new BufferedReader( new InputStreamReader(client.getInputStream()) );
            // Create writer to send text back to this client
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            // blocks until this specific client sends a message
            String msg = in.readLine();
            System.out.println("Received: " + msg);

            // send echo back to this specific client
            out.println("Echo: " + msg);

            // close this client's socket
            client.close();

        } catch (IOException e) {
            // print error but don't crash the whole server - other clients keep working
            System.err.println("Client error: " + e.getMessage());
        }
    }
}