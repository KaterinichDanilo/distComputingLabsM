package Lab6;

import java.io.*;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String INPUT_FILE_NAME = "files/words_alpha.txt";
    private static int threads = 4;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Started reading the dictionary...");
        readDataFromFileThread();
        Thread.currentThread().sleep(2000);

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        executorService.submit(new WorkerThread());
        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The number of unique words: " + WorkerThread.getNumberOfUniqueWords());
        System.out.println("The longest words:");
        TreeSet<String> theLongestWordsSet = WorkerThread.getTheLongestWordsSet();
        for (String word : theLongestWordsSet) {
            System.out.println(word);
        }
    }

    private static void readDataFromFileThread(){
        Thread thread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    WorkerThread.getQueue().add(line);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);}
        });
        thread.start();
    }
}
