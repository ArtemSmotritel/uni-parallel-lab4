package interfaces;

public interface Philosopher {
    void eat();
    boolean isEating();
    void setLeftFork(Fork fork);
    void setRightFork(Fork fork);
}
