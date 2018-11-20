package edu.javacourse.synch;

public class Starter
{

    // launching working functions as threads

    public static void main(String[] args) throws InterruptedException {
        example1();
        example2();
    }

    private static void example1() throws InterruptedException {
        CommonClass cc = new CommonClass();

        for (int i = 0; i < 10; i++) {
            ListThread lt = new ListThread(cc);
            new Thread(lt).start();
        }
        Thread.sleep(1000);

    }

    private static void example2() throws InterruptedException {
        CommonClass2 cc = new CommonClass2();

        for (int i = 0; i < 10; i++) {
            ListThread2 lt = new ListThread2(cc);
            new Thread(lt).start();
        }
        Thread.sleep(1000);

    }

}
