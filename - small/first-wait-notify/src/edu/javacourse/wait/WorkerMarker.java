package edu.javacourse.wait;

public class WorkerMarker {
    public void startFirstPart() throws Exception {
        System.out.println("First part is done!");
        wait();
        System.out.println("Second part is done!");
    }

}
