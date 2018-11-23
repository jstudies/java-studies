package edu.javacourse.wait;

public class Worker implements Runnable {

    public WorkerMarker marker;

    @Override
    public void run() {
        try {
            marker.working();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
