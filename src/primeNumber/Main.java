package primeNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


class PrimeMonitor {
    private List<Integer> primes = new ArrayList<>();

    public synchronized void addPrime(int nPrime) {
        primes.add(nPrime);
    }

    public List<Integer> getPrimes() {
        return primes;
    }
}

class PrimeRun implements Runnable {
    private int anInt;
    private PrimeMonitor monitor;
    int newNumber;

    public PrimeRun( int end, PrimeMonitor monitor) {
        this.anInt = end;
        this.monitor = monitor;
    }
    static int number = 0;
    static ReentrantLock lock = new ReentrantLock();
    public void run() {
        String threadName = Thread.currentThread().getName();
        while(number < anInt) {
            lock.lock();
            try {
                newNumber = number;
                if (newNumber >= anInt) {
                    break;
                }
                number = newNumber + 1;
            }finally {
                lock.unlock();
            }
            if (isPrime(newNumber)) {
                monitor.addPrime(newNumber);
                //System.out.println(threadName + " found prime: " + newNumber);
            }
        }
    }

    public static boolean isPrime(int n){
        if (n == 2 || n == 3 || n == 5) return true;
        if (n <= 1 || (n&1) == 0) return false;

        for (int i = 3; i*i <= n; i += 2)
            if (n % i == 0) return false;

        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        final int numThreads = Runtime.getRuntime().availableProcessors();
        //final int numThreads = 4;
        final int range = 1_000_000;

        PrimeMonitor monitor = new PrimeMonitor();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new PrimeRun(range, monitor));
            threads.add(thread);
        }
        long start = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();

        List<Integer> primes = monitor.getPrimes();
        System.out.println(" prime numbers: " + primes);
        System.out.println("Found " + primes.size() + " prime numbers");
        System.out.println("Number of threads: " + numThreads);
        System.out.println("Time taken: " + (end - start));
    }
}
