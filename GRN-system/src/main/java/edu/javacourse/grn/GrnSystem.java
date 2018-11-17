package edu.javacourse.grn;

import java.net.ServerSocket;
import java.net.Socket;

public class GrnSystem {
    public static void main(String[] args) {
        GrnSystem grn = new GrnSystem();
        grn.start();
    }

    private void start() {
        try {
            ServerSocket ses = new ServerSocket(7777); // setup incoming socket

            System.out.println(this.getClass() + " is listening at " + ses);
            System.out.println("\n");
            System.out.println("---");

            while (true) {
                Socket socket = ses.accept(); // set socket to accept
                RequestHandler rh = new RequestHandler(socket);
                new Thread(rh).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
