package io.hedwig.dslsamples.trading;

/**
 * Created by patrick on 16/1/1.
 */
public class Order {
    private final String security;
    private final int quantity;
    private final int limitPrice;
    private final boolean allOrNone;
    private int value;
    private final String boughtOrSold;

    public String getSecurity() {
        return security;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getLimitPrice() {
        return limitPrice;
    }

    public boolean isAllOrNone() {
        return allOrNone;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getBoughtOrSold() {
        return boughtOrSold;
    }


    static class Builder {
        private String security;
        private int quantity;
        private int limitPrice;
        private boolean allOrNone;
        private int value;
        private String boughtOrSold;

        public Builder() {
        }

        public Builder buy(int quantity, String security) {
            this.boughtOrSold = "Bought";
            this.quantity = quantity;
            this.security = security;
            return this;
        }

        public Builder sell(int quantity, String security) {
            this.boughtOrSold = "Sold";
            this.quantity = quantity;
            this.security = security;
            return this;
        }

        public Builder atLimitPrice(int p) {
            this.limitPrice = p;
            return this;
        }

        public Builder allOrNone() {
            this.allOrNone = true;
            return this;
        }

        public Builder valueAs(OrderValuer ov) {
            this.value = ov.valueAs(quantity, limitPrice);
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }


    private Order(Builder b) {
        security = b.security;
        quantity = b.quantity;
        limitPrice = b.limitPrice;
        allOrNone = b.allOrNone;
        value = b.value;
        boughtOrSold = b.boughtOrSold;
    }
}
