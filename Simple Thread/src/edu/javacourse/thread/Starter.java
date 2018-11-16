package edu.javacourse.thread;

public class Starter {



    public static void main(String[] args) {
        for (int i=0;i<5;i++) {
            MyThread mt = new MyThread("Name: "+i);
//            mt.setThreadName();
            mt.start();
            System.out.println("Started");
        }
    }
}
