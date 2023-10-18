package Lab2;

import Lab2.Task1.PrintHelloWorld;
import Lab2.Task2.MultiplyMatricesThread;
import Lab2.Task3.SumAllElementsInMatrixThead;
import Lab2.Task3.SumElementsInRowsMatrix;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    private final static int THREADSAMOUNT = 4;
    private final static int N = 500;
    private static long startTime, endTime;
    private static List<Thread> threadList;

    public static void main(String[] args) {
        //Task 1
//        System.out.println("Task 1");
//        PrintHelloWorld.printHelloWorld();

        //Task2
//        System.out.println("Task 2");
//        int [][] matrixA = getRandomArray(N);
//        int [][] matrixB = getRandomArray(N);
//
//        int [][] resultMatrix = new int[N][N];
//
//        startTime = System.currentTimeMillis();
//        threadList = new LinkedList<>();
//        for (int i = 0; i < THREADSAMOUNT; i++) {
//            int rowStart = i * resultMatrix.length/THREADSAMOUNT;
//            int rowEnd = (i+1) * resultMatrix.length/THREADSAMOUNT;
//            Thread thread = new Thread(new MultiplyMatricesThread(matrixA, matrixB, resultMatrix, rowStart, rowEnd));
//            thread.start();
//            threadList.add(thread);
//        }
//
//        for (Thread t:threadList) {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        endTime = System.currentTimeMillis();
//        System.out.println("Time: " + (endTime - startTime) + " milliseconds");
//        System.out.println("First row in multiplied matrix: ");
//        for (int i = 0; i < resultMatrix[0].length; i++) {
//            System.out.print(resultMatrix[0][i] + " ");
//        }
//        System.out.println();


//        Task3

//        Вивести суму елементів строки матриці.
        System.out.println("Task 3: Output the sum of the row elements of the matrix.");

        int [][] matrixC = getRandomArray(N);
        int [] sumElementsInRowMatrixC = new int[N];

        threadList = new LinkedList<>();
        startTime = System.currentTimeMillis();

        for (int i = 0; i < THREADSAMOUNT; i++) {
            Thread thread = new SumElementsInRowsMatrix(matrixC, i*matrixC.length/THREADSAMOUNT,
                    (i+1)*matrixC.length/THREADSAMOUNT, sumElementsInRowMatrixC);
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

        endTime = System.currentTimeMillis();
        System.out.println("Time " + (endTime - startTime) + " milliseconds");
        for (int i = 0; i < N; i++) {
            System.out.print(sumElementsInRowMatrixC[i] + " ");
        }
        System.out.println();

//        Вивести суму всіх елементів всіх строк матриці з використанням редукції.
        System.out.println("Derive the sum of all elements of all terms of the matrix using reduction");
        int[][] matrixD = getRandomArray(N);
        threadList = new LinkedList<>();
        startTime = System.currentTimeMillis();

        for (int i = 0; i < THREADSAMOUNT; i++) {
            Thread thread = new SumAllElementsInMatrixThead(matrixD, i*matrixD.length/THREADSAMOUNT,
                    (i+1)*matrixD.length/THREADSAMOUNT);
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
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " milliseconds");
        System.out.println("Sum of all elements = " + SumAllElementsInMatrixThead.getTotalSum());
    }

    private static int[][] getRandomArray(int n){
        Random random = new Random();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
        return matrix;
    }
}
