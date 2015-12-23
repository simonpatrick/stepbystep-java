package io.hedwig.concurrent.deadlocksample.hiddendeadlock;

class Deadlocker {
    int field_1;
    private Object lock_1 = new int[1];
    int field_2;
    private Object lock_2 = new int[1];

    public void method1(int value) {
        synchronized (lock_1) {
            synchronized (lock_2) {
                field_1 = 0;
                field_2 = 0;
            }
        }
    }

    public void method2(int value) {
        synchronized (lock_2) {
            synchronized (lock_1) {
                field_1 = 0;
                field_2 = 0;
            }
        }
    }
}
 
