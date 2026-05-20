package org.example;

import java.net.*;

public class UDPReceiver {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(1234);
        byte[] buffer = new byte[1024];
        System.out.println("UDP Receiver waiting on port 1234...");

        while (true) {
            // DatagramPacket wraps data + sender address
            DatagramPacket packet =
                    new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);   // BLOCKS until packet arrives

            String received = new String(
                    packet.getData(), 0, packet.getLength());

            System.out.println("From " + packet.getAddress()
                    + " : " + received);

        }
    }
}
