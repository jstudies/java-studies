package edu.javasource.thread;

public class SimpleRunnable01 implements Runnable {

    @Override
    public void run() {
        System.out.println("Simple Runnable 01");
        if (true){
            throw new RuntimeException("Exception 01");
        }
        System.out.println("Simple Runnable 01 - FINISH");
    }
}
