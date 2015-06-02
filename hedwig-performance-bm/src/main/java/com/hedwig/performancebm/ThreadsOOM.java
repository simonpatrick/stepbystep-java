package com.hedwig.performancebm;

/**
 * Created by patrick on 15/5/26.
 *
 * @version $Id$
 */

/**
 * -Xmx512m 减少堆内存
 *-Xmx1g -Xss128k
 */
public class ThreadsOOM {
    public static void main(String args[]){
        for(int i=0;i<150000;i++){
            new Thread(new SleepThread(),"Thread"+i).start();
            System.out.println("Thread"+i+" created");
        }
    }

    private static class SleepThread  implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
