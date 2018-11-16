package edu.javacourse.thread;

import java.util.ArrayList;
import java.util.List;

public class Starter {


    public static void main(String[] args) throws InterruptedException {
        List<MyThread> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyThread mt = new MyThread("Name: " + i);
            mt.start();
            list.add(mt);
        }
        System.out.println("Started");
        Thread.sleep(1000);
        for (MyThread mt:list){
            mt.stopThread();
//            System.out.println("Stopped");
        }
    }
}
