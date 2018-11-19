package edu.javacourse.thread;

public class LocalExample implements Runnable
{
    private ThreadLocal<Counter> localCounter = new ThreadLocal<Counter>()
    {
        @Override
        protected Counter initialValue() {
            System.out.println("OK");
            return new Counter();
        }
    };

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            localCounter.get().increase();
        }
    }

    public Counter getCounter() {
        return localCounter.get();
    }
}
