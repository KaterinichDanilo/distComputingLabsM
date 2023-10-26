package Lab1;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MultiplicationThread extends Thread {

    private static Double sharedVariable = Math.pow(10, 2);
    private static double a = Math.pow(10, -9), b = Math.pow(10, 11);
    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static boolean toStop = false;

    private double multiplierNumber;
    private int priority;


    public MultiplicationThread(double multiplierNumber, int priority) {
        this.multiplierNumber = multiplierNumber;
        this.priority = priority;
    }

    public static void setSharedVariable(double sharedVariable) {
        MultiplicationThread.sharedVariable = sharedVariable;
        MultiplicationThread.toStop = false;
    }

    public static void setA(double a) {
        MultiplicationThread.a = a;
    }

    public static void setB(double b) {
        MultiplicationThread.b = b;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        readWriteLock.readLock().lock();
        double currentValueOfSharedVariable = sharedVariable;
        readWriteLock.readLock().unlock();

        while (currentValueOfSharedVariable > a && currentValueOfSharedVariable < b && !toStop) {
            readWriteLock.writeLock().lock();
            sharedVariable *= multiplierNumber;
            currentValueOfSharedVariable = sharedVariable;
            System.out.println("Ім'я потоку: " + Thread.currentThread().getName() + "; пріоритет: " + Thread.currentThread().getPriority() + "; значення sharedVariable: " + currentValueOfSharedVariable);
            readWriteLock.writeLock().unlock();
        }
        toStop = true;

    }
}
