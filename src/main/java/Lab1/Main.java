package Lab1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Введіть множник та пріоритет для першого потоку:");
            String dataInput = in.nextLine();
            double multiplierNumber = Double.parseDouble(dataInput.split(" ")[0]);
            int priority = Integer.parseInt(dataInput.split(" ")[1]);
            System.out.println(multiplierNumber + " " + priority);

            Thread thread = new MultiplicationThread(multiplierNumber, priority);

            System.out.println("Введіть множник та пріоритет для другого потоку:");
            dataInput = in.nextLine();
            multiplierNumber = Double.parseDouble(dataInput.split(" ")[0]);
            priority = Integer.parseInt(dataInput.split(" ")[1]);
            System.out.println(multiplierNumber + " " + priority);

            Thread thread2 = new MultiplicationThread(multiplierNumber, priority);

            System.out.println("Початок роботи потоків: ");
            thread.start();
            thread2.start();
            try {
                thread.join();
                thread2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Роботу потоків завершено!");
            MultiplicationThread.setSharedVariable(Math.pow(10, 2));

        }
    }
}
