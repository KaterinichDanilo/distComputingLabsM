package Lab3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class IntegralCalculator {
    private double integralValue;
    private class CountIntegralThread extends Thread{
        private double a, b, h, sum = 0;
        private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public CountIntegralThread(double a, double b, double h) {
            this.a = a;
            this.b = b;
            this.h = h;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " " + a + " " + b);
            for (double i = a; i < b; i+=h) {
                sum += Function.func(i);
            }
            readWriteLock.writeLock().lock();
            integralValue += sum;
            readWriteLock.writeLock().unlock();
        }
    }

    public double countIntegral(double a, double b, int n, int threadsAmount) {
        integralValue = 0;
        double h = (b - a)/n;

        List<Thread> threadList = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (long i = 0; i < threadsAmount; i++) {
            Thread thread = new CountIntegralThread(a + h + h*(i * (n - 1)/threadsAmount),
                    a + h + h*((i + 1) * (n - 1)/threadsAmount), h);
            thread.start();
            threadList.add(thread);
        }
        for (Thread t:threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        integralValue = h * ((Function.func(a) + Function.func(b)) / 2 + integralValue);

        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " milliseconds");
        return integralValue;
    }

}
