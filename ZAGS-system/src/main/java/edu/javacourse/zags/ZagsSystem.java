package edu.javacourse.zags;

import java.net.ServerSocket;
import java.net.Socket;


public class ZagsSystem {
    public static void main(String[] args) {
        ZagsSystem grn = new ZagsSystem();
        grn.start();
    }

    private void start() {
        try {
            ServerSocket ses = new ServerSocket(7779); // setup incoming socket

            System.out.println(this.getClass() + " is listening at " + ses);
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
