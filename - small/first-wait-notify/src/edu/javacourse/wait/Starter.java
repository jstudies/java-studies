package edu.javacourse.wait;

public class Starter {
    public static void main(String[] args) {
        WorkerMarker wm= new WorkerMarker();
        for (int i=0;i<3; i++){
            Worker w=new Worker();
            w.marker=wm;
            new Thread(w).start();
        }
    }
}
