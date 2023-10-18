package Lab2.Task1;

public class PrintHelloWorld {
    public static void printHelloWorld() {
        int amountOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(amountOfThreads);

        for (int i = 0; i < amountOfThreads; i++) {
            Thread thread = new Thread(new PrintHelloWorldThread());
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class PrintHelloWorldThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": Hello World");
        }
    }

}
