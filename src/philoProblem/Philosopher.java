package philoProblem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Philosopher extends Thread {
    private Table table;
    private int position;
    private DateTimeFormatter formatter;

    public Philosopher(Table table, int position) {
        this.table = table;
        this.position = position;
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    public void run() {
        try {
            while (true) {
                table.TakeForks(position);
                System.out.println(LocalTime.now().format(formatter) + " - Philosopher " + position + " is eating.");
                Thread.sleep(1000); // Simulate eating
                table.ReturnForks(position);
                System.out.println(LocalTime.now().format(formatter) + " - Philosopher " + position + " is thinking.");
                Thread.sleep(5000); // Simulate thinking
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}