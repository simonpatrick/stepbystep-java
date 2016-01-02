package io.hedwig.dslsamples.trading;

import io.hedwig.dslsamples.trading.OrderValuer;

public class StandardOrderValuer implements OrderValuer {
    public int valueAs(int qty, int unitPrice) {
        return unitPrice * qty;
    }
}