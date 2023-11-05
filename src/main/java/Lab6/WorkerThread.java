package Lab6;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WorkerThread implements Runnable{

    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static int numberOfUniqueWords = 0;
    private static TreeSet<String> theLongestWordsSet = new TreeSet<>(Comparator.comparingInt(String::length).reversed());
    private static int numberOfLongestWords = 10;
    private static int currentIndex = 0;

    public static BlockingQueue<String> getQueue(){
        return queue;
    }

    public static TreeSet<String> getTheLongestWordsSet() {
        return theLongestWordsSet;
    }

    public static int getNumberOfUniqueWords() {
        return numberOfUniqueWords;
    }

    public static void setNumberOfUniqueWords(int numberOfUniqueWords) {
        WorkerThread.numberOfUniqueWords = numberOfUniqueWords;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String word = queue.poll(5, TimeUnit.SECONDS);
                boolean hasDuplicate = hasDuplicateCharacters(word);
                readWriteLock.writeLock().lock();
                currentIndex++;
                if (currentIndex % 10000 == 0) {
                    System.out.println("Words are processed: " + currentIndex + "; Unique words: " + numberOfUniqueWords);
                }
                readWriteLock.writeLock().unlock();

                if (!hasDuplicate) {
                    readWriteLock.writeLock().lock();
                    numberOfUniqueWords++;
                    readWriteLock.writeLock().unlock();
                }
                readWriteLock.writeLock().lock();
                theLongestWordsSet.add(word);
                int setSize = theLongestWordsSet.size();
                readWriteLock.writeLock().unlock();

                if (setSize > numberOfLongestWords) {
                    readWriteLock.writeLock().lock();
                    theLongestWordsSet.pollLast();
                    readWriteLock.writeLock().unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean hasDuplicateCharacters(String str) {
        Set<Character> charSet = new HashSet<>();

        for (char c : str.toCharArray()) {
            if (!charSet.add(c)) {
                return true;
            }
        }
        return false;
    }
}
