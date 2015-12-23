package io.hedwig.concurrent.thread.state;

import java.util.concurrent.TimeUnit;

/**
 * Created by patrick on 15/11/27.
 */
public class ThreadJoinDemo {

    public static void main(String[] args) {
        //start a thread and throw exception after sleep 3 senconds
        final Thread t = new Thread(){
            @Override
            public void run() {
                System.out.println(getName() + "start.");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    throw new InterruptedException();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + "end.");
            }

        };
        t.start();

        //start another thread and sleep 10s
        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println(getName() + "start.");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + "end.");
            }

        };

        //start another threads
        new Thread() {
            public void run() {
                try {
                    System.out.println("start kill...");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("kill end ...");
            }
        }.start();

        //join threads
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("EEEE");
            e.printStackTrace();
        }
        System.out.println("...");
        t2.start();
    }
}
