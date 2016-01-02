package io.hedwig.dslsamples.trading;

public interface OrderValuer {
    int valueAs(int qty, int unitPrice);
}