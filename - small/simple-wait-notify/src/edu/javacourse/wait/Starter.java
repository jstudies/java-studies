package edu.javacourse.wait;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws Exception {
        List<Worker> list= new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Worker w = new Worker();
            list.add(w);
            new Thread(w).start();
        }
        Thread.sleep(2000);
        for(Worker w:list){
            w.goOn=true;
        }
    }
}
