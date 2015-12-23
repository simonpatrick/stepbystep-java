package com.hedwig.listeners;

import com.google.common.collect.Maps;
import com.hedwig.testlogs.TestNGEnhancedTest;
import com.hedwig.testlogs.TestResultLogger;
import com.hedwig.testlogs.listeners.TestStepLoggerListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by patrick on 15/11/16.
 */
public class TestStepLoggerListenerTest extends TestNGEnhancedTest {
    Map<Integer,String> map = Maps.newConcurrentMap();

    @Test(invocationCount = 50,threadPoolSize = 50)
    public void testLoggerLifeCycle() throws Exception {
        TestStepLoggerListener listener = TestStepLoggerListener.getInstance();
        map.put(listener.hashCode(), "test1");
        TestResultLogger.log("abcdstest" + Thread.currentThread().getId());
    }

    @Test(invocationCount = 50,threadPoolSize =50)
    public void testInitLogger() throws Exception {
        TestStepLoggerListener listener = TestStepLoggerListener.getInstance();
        map.put(listener.hashCode(), "test");
        TestResultLogger.log("abcdstest" + Thread.currentThread().getId());
        System.out.println(listener);
        Assert.assertEquals(map.size(), 1);
    }

}