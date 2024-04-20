import interfaces.Fork;
import interfaces.Philosopher;

public class SimplePhilosopher implements Philosopher {
    private static int ID = 0;
    private Fork leftFork;
    private Fork rightFork;
    private final int id;
    private volatile boolean isEating = false;

    @Override
    public void setLeftFork(Fork fork) {
        System.out.printf("%s with %s left fork\n", this, fork);
        leftFork = fork;
    }

    @Override
    public void setRightFork(Fork fork) {
        System.out.printf("%s with %s right fork\n", this, fork);
        rightFork = fork;
    }

    public SimplePhilosopher(Fork leftFork, Fork rightFork) {
        this.id = ++ID;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public SimplePhilosopher() {
        this.id = ++ID;
    }

    @Override
    public void eat() {
        isEating = true;
        System.out.printf("%s wants to take a left fork [%s]\n", this, leftFork);
        leftFork.take();
        System.out.printf("%s took the left fork [%s]\n", this, leftFork);
        System.out.printf("%s wants to take a right fork [%s]\n", this, rightFork);
        rightFork.take();
        System.out.printf("%s took the right fork [%s]\n", this, rightFork);
        System.out.printf("%s IS EATING\n", this);

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        rightFork.release();
        System.out.printf("%s released the right fork [%s]\n", this, rightFork);
        leftFork.release();
        System.out.printf("%s released the left fork [%s]\n", this, leftFork);
        isEating = false;
        System.out.printf("%s CEASED EATING\n", this);
    }

    @Override
    public boolean isEating() {
        return isEating;
    }

    @Override
    public String toString() {
        return String.format("Philosopher [%d]", id);
    }
}
