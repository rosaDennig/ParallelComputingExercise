package philoProblem;

public class Main {
    public static void main(String[] args) {
        int numPhilosophers = 5;
        Table table = new Table(numPhilosophers);

        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i] = new Philosopher(table, i);
            philosophers[i].start();
        }
    }
}