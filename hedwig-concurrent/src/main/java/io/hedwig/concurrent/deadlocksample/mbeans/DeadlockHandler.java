package io.hedwig.concurrent.deadlocksample.mbeans;

import java.lang.management.ThreadInfo;

public interface DeadlockHandler {
  void handleDeadlock(final ThreadInfo[] deadlockedThreads);
}