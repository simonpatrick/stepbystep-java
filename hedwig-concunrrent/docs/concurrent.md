# JAVA Concurrent

基础概念:
- 原子操作: java.util.concurrent.atomic
- 锁 : java.util.concurrent.locks
- 线程协作 : java.util.concurrent

## Thread(线程)- state

线程的状态有:

- new
- runnable
- wait
- timed_wait
- blocked
- terminated

## Thread-Join

```
The join method allows one thread to wait for the completion of another. If t is a Thread object whose thread is currently executing,

t.join();
causes the current thread to pause execution until t's thread terminates. Overloads of join allow the programmer to specify a waiting period. However, as with sleep, join is dependent on the OS for timing, so you should not assume that join will wait exactly as long as you specify.

Like sleep, join responds to an interrupt by exiting with an InterruptedException.

```


