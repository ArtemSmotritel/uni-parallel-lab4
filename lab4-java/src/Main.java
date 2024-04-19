import interfaces.Fork;
import interfaces.Philosopher;
import interfaces.Table;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int PHILOSOPHERS_COUNT = 5;
    private static final int FORKS_COUNT = 5;

    public static void main(String[] args) {
        List<Philosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            philosophers.add(new SimplePhilosopher());
        }

        List<Fork> forks = new ArrayList<>();
        for (int i = 0; i < FORKS_COUNT; i++) {
            forks.add(new SimpleFork());
        }

        Table table = new SimpleTable(forks, philosophers);

        table.run();
    }
}