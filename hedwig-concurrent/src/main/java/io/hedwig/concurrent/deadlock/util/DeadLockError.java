package io.hedwig.concurrent.deadlock.util;

/**
 * Created by patrick on 15/11/27.
 */
public class DeadLockError extends Error{
    private final Thread thread;

    public DeadLockError(Thread thread) {
        super("Deadlock involving thread: " + thread);
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }
}
