import interfaces.Fork;
import interfaces.Philosopher;
import interfaces.Table;

import java.util.List;

public class TableWithSwitchingForks implements Table {
    private final List<Fork> forks;
    private final List<Philosopher> philosophers;

    public TableWithSwitchingForks(List<Fork> forks, List<Philosopher> philosophers) {
        this.forks = forks;
        this.philosophers = philosophers;
    }

    @Override
    public int countEatingPhilosophers() {
        return (int) philosophers.stream().filter(Philosopher::isEating).count();
    }

    @Override
    public void run() {
        if (philosophers.size() != forks.size()) {
            throw new IllegalArgumentException("There should be an even amount of both the forks and the philosophers");
        }

        for (int i = 0; i < philosophers.size(); i++) {
            philosophers.get(i).setLeftFork(forks.get(i));
            philosophers.get(i).setRightFork(forks.get((i + 1) % forks.size()));
        }

        final var lastPhilosopherIndex = philosophers.size() - 1;
        philosophers.get(lastPhilosopherIndex).setRightFork(forks.get(lastPhilosopherIndex));
        philosophers.get(lastPhilosopherIndex).setLeftFork(forks.get((lastPhilosopherIndex + 1) % forks.size()));

        System.out.println("---------------------------------------------------");

        for (Philosopher philosopher : philosophers) {
            new Thread(philosopher::eat).start();
        }
    }
}
