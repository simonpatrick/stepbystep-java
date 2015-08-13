package com.hedwig.ut.samples.asynch;

public interface AuctionProcessor {
    AuctionState process(Item item, int bid);
}