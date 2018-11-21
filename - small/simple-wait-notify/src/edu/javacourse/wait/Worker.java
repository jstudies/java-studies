package edu.javacourse.wait;

public class Worker implements Runnable {

    public volatile boolean goOn=false;

    @Override
    public void run() {
        System.out.println("First part is done");
        while(!goOn){
            ;
        }
        System.out.println("Second part is done");
    }
}

