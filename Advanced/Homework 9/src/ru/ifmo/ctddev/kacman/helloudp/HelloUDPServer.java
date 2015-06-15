package ru.ifmo.ctddev.kacman.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloServer;
import javafx.util.Pair;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Server which is working using User Datagram Protocol (UDP).
 *
 * @see ru.ifmo.ctddev.kacman.helloudp.HelloUDPClient
 * @author Alexey Katsman
 */

public class HelloUDPServer implements HelloServer {

    private final static int TIMEOUT = 1000;
    private final static String ENCODING = "UTF-8";
    private final static String HELLO = "Hello, ";
    private final static ArrayList<Pair<DatagramSocket, Thread[]>> sockets = new ArrayList<>();

    @Override
    public void start(int port, int threads) {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            socket.setSoTimeout(TIMEOUT);
            Thread[] threadsList = new Thread[threads];

            for (int i = 0; i < threads; i++) {
                threadsList[i] = new Thread(new Helper(socket));
            }

            synchronized (sockets) {
                sockets.add(new Pair<>(socket, threadsList));

                for (int i = 0; i < threads; i++) {
                    threadsList[i].start();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public synchronized void close() {
        for (Pair<DatagramSocket, Thread[]> elem : sockets) {
            Thread[] thread = elem.getValue();

            for (Thread aThread : thread) {
                aThread.interrupt();
            }
        }

        try {
            for (Pair<DatagramSocket, Thread[]> elem : sockets) {
                Thread[] thread = elem.getValue();

                for (Thread aThread : thread) {
                    aThread.join();
                }
            }
        } catch (InterruptedException ignored) {

        }

        for (Pair<DatagramSocket, Thread[]> elem : sockets) {
            elem.getKey().close();
        }

        sockets.clear();
    }

    private class Helper implements Runnable {

        final DatagramSocket socketReceive;

        public Helper(DatagramSocket socketReceive) {
            this.socketReceive = socketReceive;
        }

        @Override
        public void run() {
            byte bufReceive[] = new byte[1024];

            try {
                bufReceive = new byte[socketReceive.getReceiveBufferSize()];
            } catch (SocketException ignored) {

            }

            DatagramPacket packetSend = new DatagramPacket(bufReceive, bufReceive.length);

            try (DatagramSocket socketSend = new DatagramSocket()) {
                while (!Thread.interrupted()) {
                    try {
                        socketReceive.receive(packetSend);
                    } catch (SocketTimeoutException e) {
                        continue;
                    }

                    String text = HELLO + new String(packetSend.getData(), packetSend.getOffset(), packetSend.getLength(), ENCODING);
                    byte bufSend[] = text.getBytes(ENCODING);
                    packetSend = new DatagramPacket(bufSend, bufSend.length, packetSend.getAddress(), packetSend.getPort());
                    socketSend.send(packetSend);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}