package io.hedwig.concurrent.deadlocksample;

public class SyncThread implements Runnable{
    private Object obj1;
    private Object obj2;
//    private static Object obj3 = new Object();

    public SyncThread(Object o1, Object o2){
        this.obj1=o1;
        this.obj2=o2;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " acquiring lock on object 1"+obj1);
        synchronized (obj1) {
         System.out.println(name + " acquired lock on object 1"+obj1);
         work();
         System.out.println(name + " acquiring lock on object 2"+obj2);
         synchronized (obj2) {
            System.out.println(name + " acquired lock on  object 2"+obj2);
            work();
        }
         System.out.println(name + " released lock on object 2"+obj2);
        }
        System.out.println(name + " released lock on object 1"+obj1);
        System.out.println(name + " finished execution.");
    }

    private void work() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}