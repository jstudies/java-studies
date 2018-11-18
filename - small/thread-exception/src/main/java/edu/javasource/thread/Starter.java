package edu.javasource.thread;

public class Starter {
    public static void main(String[] args) throws InterruptedException {
        testOld();
        Thread.sleep(500);
        System.out.println("FINISH Global");
    }

    private static void testOld() {
        SimpleRunnable01 s01 = new SimpleRunnable01();
        Thread t = new Thread(s01);
        t.start();
    }

}
