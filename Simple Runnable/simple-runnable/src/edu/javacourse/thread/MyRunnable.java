package edu.javacourse.thread;

public class MyRunnable implements Runnable {

    private String ThreadName;
    private volatile boolean running = true;


    public MyRunnable(String threadName) {
        this.ThreadName = threadName;
    }

    public void stopThread() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Hello: " + ThreadName);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
