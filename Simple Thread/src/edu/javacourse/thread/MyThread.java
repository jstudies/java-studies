package edu.javacourse.thread;

public class MyThread extends Thread {

    private String ThreadName;
    public MyThread(String threadName){
        this.ThreadName=threadName;
    }

    public String getThreadName() {
        return ThreadName;
    }

    public void setThreadName(String threadName) {
        ThreadName = threadName;
    }

//    int i=0;
    @Override
    public void run() {
        while (true) {
//            System.out.println("Hello "+i+"("+this.getId()+")");
            System.out.println("Hello: "+ThreadName);
//            i=i+1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
