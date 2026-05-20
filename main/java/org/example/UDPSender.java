package org.example;

import java.net.*;

public class UDPSender {
    public static void main(String[] args) throws Exception {
        // create UDP socket; no port specified = OS assigns random ephemeral port automatically
        DatagramSocket socket = new DatagramSocket();

        String message = "Hello UDP!";

        // Convert string to bytes - UDP works with raw bytes
        byte[] data = message.getBytes();

        InetAddress address = InetAddress.getByName("localhost");

        // Packet carries: data + length + destination address + port
        DatagramPacket packet = new DatagramPacket(
                data, data.length, address, 1234
        );

        socket.send(packet);    // fire and forget — no blocking
        System.out.println("Sent (no confirmation will come)");

        socket.close();
    }
}

