package edu.javacourse.student;

import java.net.ServerSocket;
import java.net.Socket;

public class StudentSystem {
    public static void main(String[] args) {
        StudentSystem grn = new StudentSystem();
        grn.start();
    }

    private void start() {
        try {
            ServerSocket ses = new ServerSocket(7778); // setup incoming socket

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
