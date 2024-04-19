package interfaces;

public interface Fork {
    void take();
    boolean isFree();
    void release();
}
