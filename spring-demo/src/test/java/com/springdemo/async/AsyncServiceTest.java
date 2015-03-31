package com.springdemo.async;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import javax.inject.Inject;

import java.util.concurrent.FutureTask;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-async.xml")
public class AsyncServiceTest {

    @Inject
    private AsyncService asyncService ;
    @Inject
    private SyncService syncService;
    @Test
    public void testAsync() throws InterruptedException {
        asyncService.throwException();
        Thread.sleep(1000L);
    }

    @Test
    public void testAsyncGet1() throws Exception {
        assertEquals(null, asyncService.asyncGet1());
        Thread.sleep(1000L);
    }

    @Test
    public void testSyncGet1(){
        System.out.println("start:"+System.currentTimeMillis());
        asyncService.asyncGet1();
        asyncService.asyncGet1();
        asyncService.asyncGet1();

        System.out.println("end: "+System.currentTimeMillis());

    }
    @Test
    public void testAsyncGet2() throws Exception {
        ListenableFuture<String> listenableFuture = asyncService.asyncGet2();
        SuccessCallback<String> successCallback = new SuccessCallback<String>() {
            @Override
            public void onSuccess(String str) {
                System.out.println("on success message, return : " + str+" su ");
            }
        };
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("on failure , exception message : " + throwable.getMessage());
            }
        };
        asyncService.throwException();
        asyncService.asyncGet2("tested message");
        listenableFuture.addCallback(successCallback, failureCallback);
        assertEquals("asyncGet1:1123", listenableFuture.get());
    }
}