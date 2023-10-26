package Lab5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int N = 1000, threadsAmount = 5;
        final double W = 1.0/N;
        long startTime, endTime;
        List<Thread> threadList;
        double pi;

//        Task1

        startTime = System.currentTimeMillis();
        threadList = new ArrayList<>();
        for (int i = 0; i < threadsAmount; i++) {
            Thread thread = new Thread(new PiCalculateThread(i * N/threadsAmount, (i + 1)*N/threadsAmount, W));
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

        pi = W * PiCalculateThread.getSum();
        System.out.println(pi);
        endTime = System.currentTimeMillis();
        System.out.println("Multithreading took " + (endTime - startTime) + " milliseconds");

//        Task2

        int numSamples = 100000000;
        double piApproximation = approximatePi(numSamples);
        System.out.println("Approximated Pi: " + piApproximation);

        startTime = System.currentTimeMillis();
        threadList = new ArrayList<>();
        for (int i = 0; i < threadsAmount; i++) {
            Thread thread = new Thread(new ApproximatePiThread(i * numSamples/threadsAmount, (i + 1)*numSamples/threadsAmount));
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
        pi = 4.0 * ApproximatePiThread.getCounter() / numSamples;
        System.out.println("PI = " + pi);
        endTime = System.currentTimeMillis();
        System.out.println("Multithreading took " + (endTime - startTime) + " milliseconds");
    }

    public static double approximatePi(int numSamples) {
        Random random = new Random();
        int counter = 0;

        for (int s = 0; s < numSamples; s++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y < 1) {
                counter++;
            }
        }

        return 4.0 * counter / numSamples;
    }
}
