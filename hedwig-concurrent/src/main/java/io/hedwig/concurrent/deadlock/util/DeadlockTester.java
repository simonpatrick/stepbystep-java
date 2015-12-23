package io.hedwig.concurrent.deadlock.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Created by patrick on 15/11/27.
 */
public class DeadlockTester {

    private final static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    /**
     * check if there is dead lock in threads
     * @param runnable
     * @throws InterruptedException
     */
    public void checkCodeDoesNotDeadLock(Runnable runnable) throws InterruptedException {

        ThreadGroup group = new ThreadGroup("testGroup");
        Thread runner = new Thread(group,runnable,"runner");
        runner.start();

        while(runner.isAlive()){
            runner.join(100);
            Thread[] threads = new Thread[group.activeCount()];
            group.enumerate(threads);

            long[] deadlocks = threadMXBean.findDeadlockedThreads();
            if(deadlocks!=null){
                for (long deadlock : deadlocks) {
                    for (Thread thread : threads) {
                        if(thread.getId()==deadlock){
                            throw new DeadLockError(thread);
                        }
                    }
                }
            }
        }
    }

    /**
     * checkout thread is terminated
     * @param thread
     * @throws InterruptedException
     */
    public void checkThatThreadTerminates(Thread thread) throws InterruptedException {
        for (int i = 0; i < 2000; i++) {
            thread.join(50);
            if (!thread.isAlive()) return;
            if (isThreadDeadlocked(thread.getId())) {
                throw new IllegalStateException("Deadlock detected!");
            }
        }
        throw new IllegalStateException(thread + " did not terminate in time");
    }

    /**
     * check if thread is dead lock
     * @param threadId
     * @return
     */
    private boolean isThreadDeadlocked(long threadId) {
        long[] ids = threadMXBean.findDeadlockedThreads();
        if (ids == null) return false;
        for (long id : ids) {
            if (id == threadId) return true;
        }
        return false;
    }
}
