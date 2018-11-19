package edu.javasource.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Starter {
    public static void main(String[] args) throws InterruptedException {
        testOld();
        testNew();
        Thread.sleep(500);
        System.out.println("FINISH Global");
    }

    private static void testNew() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<?> submit = es.submit(new SimpleRunnable01());
        try {
            Object o = submit.get();
        } catch (Exception e) {
            System.out.println("return:" + e.getClass().getCanonicalName());
            Throwable c=e.getCause();
            c.printStackTrace(System.out);
        }
        es.shutdown();
        try {
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }

    }
    private static void testOld() {
        SimpleRunnable01 s01 = new SimpleRunnable01();
        Thread t = new Thread(s01);

        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getId() + ":" + t.getState() + ":" + (e.getMessage()));
            }

        });
        t.start();
    }

    }
