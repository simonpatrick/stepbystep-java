package com.hedwig.functionaljava.introduction;


import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import static com.hedwig.functionaljava.introduction.Prices.*;

public class DiscountImperative {
  public static void main(final String[] args) {
    BigDecimal totalOfDiscountedPrices = BigDecimal.ZERO;

    for(BigDecimal price : prices) {
      if(price.compareTo(BigDecimal.valueOf(20)) > 0) 
        totalOfDiscountedPrices = 
          totalOfDiscountedPrices.add(price.multiply(BigDecimal.valueOf(0.9)));
    }
    System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
  }
}
