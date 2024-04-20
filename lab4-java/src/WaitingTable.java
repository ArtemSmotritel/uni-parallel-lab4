import interfaces.Fork;
import interfaces.Philosopher;
import interfaces.Table;

import java.util.List;
import java.util.concurrent.Semaphore;

public class WaitingTable implements Table {
    private final List<Fork> forks;
    private final List<Philosopher> philosophers;
    private final Semaphore eatingPhilosophers;

    public WaitingTable(List<Fork> forks, List<Philosopher> philosophers) {
        this.forks = forks;
        this.philosophers = philosophers;
        eatingPhilosophers = new Semaphore(forks.size() / 2);
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

        System.out.println("---------------------------------------------------");

        for (Philosopher philosopher : philosophers) {
            new Thread(() -> {
//                synchronized (this) {
//                    while (!canStartEating()) {
//                        try {
//                            wait();
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }

                try {
                    eatingPhilosophers.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                philosopher.eat();
//                synchronized (this) {
//                    notify();
//                }
                eatingPhilosophers.release();
            }).start();
        }
    }

    private boolean canStartEating() {
        return philosophers.stream()
                .filter(Philosopher::isEating)
                .count() < forks.size() / 2;
    }
}
