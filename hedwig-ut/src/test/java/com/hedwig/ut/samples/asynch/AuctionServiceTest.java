package com.hedwig.ut.samples.asynch;

import org.testng.annotations.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by patrick on 15/8/13.
 *
 * @version $Id$
 */
public class AuctionServiceTest {

    @Test
    public void shouldSendNotifications() throws InterruptedException {
        Notifier notifier = mock(Notifier.class);
        AuctionProcessor auctionProcessor = mock(AuctionProcessor.class);
        ExecutorService executorService = new ThreadPoolExecutor(2, 10, 300L,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10,true));
        Item item = mock(Item.class);
        AuctionState state = mock(AuctionState.class);
        AuctionService service = new AuctionService(notifier, auctionProcessor, executorService);
        given(auctionProcessor.process(item, 10)).willReturn(state);

        service.bid(item, 10);

        Thread.sleep(500);
        verify(notifier).sendNotifications(state);
    }
}