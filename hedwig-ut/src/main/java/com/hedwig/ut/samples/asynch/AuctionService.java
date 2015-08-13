package com.hedwig.ut.samples.asynch;

import java.util.concurrent.ExecutorService;

public class AuctionService {

	private final Notifier notifier;
	private final AuctionProcessor auctionProcessor;
	private final ExecutorService executorService;

	public AuctionService(Notifier notifier, AuctionProcessor auctionProcessor, ExecutorService executorService) {
		this.notifier = notifier;
		this.auctionProcessor = auctionProcessor;
		this.executorService = executorService;
	}

	public AuctionState bid(Item item, int bid) {
		AuctionState state = auctionProcessor.process(item, bid);
		executorService.submit(new SendNotificationTask(state));
		return state;
	}

	private class SendNotificationTask implements Runnable {
		private final AuctionState state;

		public SendNotificationTask(AuctionState state) {
			this.state = state;
		}

		public void run() {
			try {
				// something important happens here and it takes time
				// we simulate it by sleeping for some time
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			notifier.sendNotifications(state);
		}
	}
}
