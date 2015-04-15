package com.hedwig.functionaljava.designing;

import java.math.BigDecimal;
import java.util.function.Function;

public class CalculateNAV {
    public BigDecimal computeStockWorth(
            final String ticker, final int shares) {
        return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
    }

    private Function<String, BigDecimal> priceFinder;

    public CalculateNAV(final Function<String, BigDecimal> aPriceFinder) {
        priceFinder = aPriceFinder;
    }
}