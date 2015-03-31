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
public class SyncService {

    public void throwException() {
        throw new RuntimeException("error");
    }

    public String asyncGet1() {
        return "asyncGet1:1123";
    }

}
