package com.hedwig.singleton;


import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/12/2.
 */
public class LazySingletonTest {

    @Test(invocationCount = 100,threadPoolSize = 100)
    public void testGetInstance() throws Exception {
        LazySingleton s1 = LazySingleton.getInstance();
        LazySingleton s2 = LazySingleton.getInstance();
        Assert.assertEquals(s1,s2);
    }
}