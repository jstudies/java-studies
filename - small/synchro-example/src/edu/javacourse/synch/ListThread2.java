package edu.javacourse.synch;

public class ListThread2 implements Runnable
{


    private CommonClass2 cc;

    public ListThread2(CommonClass2 cc) {
        this.cc = cc;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            cc.addItem(i%2+1,Math.round(Math.random() * 100) + "");
        }
    }
}
