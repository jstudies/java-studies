package edu.javacourse.synch;

public class Starter
{

    // launching working functions as threads

    public static void main(String[] args) {
        CommonClass2 cc = new CommonClass2();

        for (int i = 0; i < 10; i++) {
            ListThread lt = new ListThread(cc);
            new Thread(lt).start();
        }
        try {
//            System.out.println(cc.getSize());
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("final size: "+cc.getSize(1));
        System.out.println("final size: "+cc.getSize(2));
    }
}
