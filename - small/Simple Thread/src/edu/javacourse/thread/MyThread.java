package edu.javacourse.thread;

public class MyThread extends Thread {

    private String ThreadName;
    private volatile boolean running = true;


    public MyThread(String threadName) {
        this.ThreadName = threadName;
    }

    public String getThreadName() {
        return ThreadName;
    }

    public void setThreadName(String threadName) {
        ThreadName = threadName;
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
