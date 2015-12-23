package io.hedwig.concurrent.deadlocksample.hiddendeadlock;

public class Queue {
    static Object queueLock_;
    Producer producer_;
    Producer.Consumer consumer_;
    private boolean done =false;

    public class Producer {
        void produce() {
            while (!done) {
                synchronized (queueLock_) {
                    produceItemAndAddItToQueue();
                    synchronized (consumer_) {
                        consumer_.notify();
                    }
                }
            }
        }

        public class Consumer {
            Consumer() throws InterruptedException {
                while (!done) {
                    synchronized (queueLock_) {
                        synchronized (consumer_) {
                            consumer_.wait();
                        }
                        removeItemFromQueueAndProcessIt();
                    }
                }
            }

            private void removeItemFromQueueAndProcessIt() {

            }
        }
    }

    private void produceItemAndAddItToQueue() {
        
    }
}
 
