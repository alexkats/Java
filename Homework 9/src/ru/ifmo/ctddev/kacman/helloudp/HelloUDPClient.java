package ru.ifmo.ctddev.kacman.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;

/**
 * Client which is working using User Datagram Protocol (UDP).
 *
 * @see ru.ifmo.ctddev.kacman.helloudp.HelloUDPServer
 * @author Alexey Katsman
 */

public class HelloUDPClient implements HelloClient {

    private final static int TIMEOUT = 1000;
    private final static String ENCODING = "UTF-8";
    private final static String HELLO = "Hello, ";

    /**
     * Starts client with provided arguments.
     *
     * @param host host IP-address
     * @param port port number for client to send data
     * @param prefix prefix of each request
     * @param requests number of requests
     * @param threads number of threads
     */

    @Override
    public void start(String host, int port, String prefix, int requests, int threads) {
        Thread threadsList[] = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadsList[i] = new Thread(new Helper(host, port, prefix, requests, i));
            threadsList[i].start();
        }

        try {
            for (int i = 0; i < threads; i++) {
                threadsList[i].join();
            }
        } catch (InterruptedException ignored) {

        }
    }

    private class Helper implements Runnable {

        final String host;
        final int port;
        final String prefix;
        final int requests;
        final int thread;

        public Helper(String host, int port, String prefix, int requests, int thread) {
            this.host = host;
            this.port = port;
            this.prefix = prefix;
            this.requests = requests;
            this.thread = thread;
        }

        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket()) {
                byte bufReceive[] = new byte[socket.getReceiveBufferSize()];
                DatagramPacket packetReceive = new DatagramPacket(bufReceive, bufReceive.length);
                socket.setSoTimeout(TIMEOUT);

                for (int i = 0; i < requests; i++) {
                    byte bufSend[] = (prefix + thread + "_" + i).getBytes(ENCODING);
                    DatagramPacket packetSend = new DatagramPacket(bufSend, bufSend.length, InetAddress.getByName(host), port);

                    while (true) {
                        socket.send(packetSend);

                        try {
                            socket.receive(packetReceive);
                            String text = new String(packetReceive.getData(), packetReceive.getOffset(), packetReceive.getLength(), ENCODING);
                            String pattern = HELLO + prefix + thread + "_" + i;
                            System.out.println(prefix + thread + "_" + i);
                            System.out.println(text);

                            if (pattern.equals(text)) {
                                break;
                            }
                        } catch (SocketTimeoutException ignored) {

                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}