package com.hedwig.functionaljava.performace;

import java.util.Arrays;
import java.util.List;

/**
 * Created by patrick on 15/4/14.
 *
 * @version $Id$
 */


public class Stocks {
    public static void main(String[] args) {
        List<Integer> prices = Arrays.asList(10, 20, 30, 40, 50, 60);

        {
            int max = 0;
            for(int price : prices) {
                if(max < price) max = price;
            }

            System.out.println(max);
        }

        final int max = prices.stream()
                .reduce(67, Math::max);
        System.out.println(max);
    }
}
