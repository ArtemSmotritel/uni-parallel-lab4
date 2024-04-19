import interfaces.Fork;

import java.util.concurrent.Semaphore;

public class SimpleFork implements Fork {
    private static int ID = 0;
    private final Semaphore access = new Semaphore(1);
    private final int id;

    public SimpleFork() {
        id = ++ID;
    }

    @Override
    public void take() {
        try {
            access.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isFree() {
        return access.availablePermits() == 1;
    }

    @Override
    public void release() {
        access.release();
    }

    @Override
    public String toString() {
        return String.format("Fork [%d]", id);
    }
}
