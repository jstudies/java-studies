package edu.javacourse.wait;

public class WorkerMarker {
    public synchronized void working() throws Exception {
        System.out.println("First part is done!");
        wait();
        System.out.println("Second part is done!");
    }

}
