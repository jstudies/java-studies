package edu.javacourse.thread;

public class Starter {

    public static void main(String[] args) {
for (int i=0;i<10;i++){
    LocalExample le= new LocalExample();
    System.out.println(i);
    new Thread(le).start();
}
    }
}
