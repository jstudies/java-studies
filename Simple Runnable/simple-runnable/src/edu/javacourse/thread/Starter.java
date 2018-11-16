package edu.javacourse.thread;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws InterruptedException {
        List<MyRunnable> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyRunnable mt = new MyRunnable("Name: " + i);
            Thread t=new Thread(mt);
            t.start();
            list.add(mt);
        }
        System.out.println("Started");
        Thread.sleep(1000);
        for (MyRunnable mt : list) {
            mt.stopThread();
        }
        annonExample();
    }
    private static void annonExample(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Annonymous");
            }
        };
        new Thread(r).start();
    }
}
