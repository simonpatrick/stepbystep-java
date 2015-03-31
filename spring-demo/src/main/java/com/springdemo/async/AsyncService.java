package com.springdemo.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by patrick on 15/3/20.
 *
 * @version $Id$
 */

@Service
@Async
public class AsyncService {

    public void throwException() {
        throw new RuntimeException("error");
    }

    public String asyncGet1() {
        return "asyncGet1:1123";
    }

    public ListenableFuture<String> asyncGet2() {
        return new AsyncResult<String>("asyncGet1:1123");
    }

    public ListenableFuture<String> asyncGet2(String message) {
        return new AsyncResult<String>(message);
    }
}
