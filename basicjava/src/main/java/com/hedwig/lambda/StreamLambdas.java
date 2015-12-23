package com.hedwig.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by patrick on 15/11/15.
 */
public class StreamLambdas {

    public static void beforeJava8(){
        List<Double> prices = Arrays.asList(1000.0,150.0,499.0);
        List<Double> newPrices = new ArrayList<>();

        for (Double price : prices) {
            System.out.println(price);
            if(price>300){
                newPrices.add(price*(1.0-0.1));
            }
        }

        Collections.sort(newPrices);
        for (Double newPrice : newPrices) {
            System.out.println(newPrice);
        }
    }

    public static void streamLambdaJava8(){
        List<Double> prices = Arrays.asList(1000.0,150.0,499.0);
        prices.stream()
                .peek(p-> System.out.println(p))
                .filter(p->p>300)
                .map(p -> p*(1-0.1))
                .sorted()
                .forEach(p-> System.out.println(p));

        System.out.println(prices);
    }

    public static void methodReferences(){

        List<Double> prices = Arrays.asList(1000.0,150.0,499.0);
        prices.stream()
                .peek(p-> System.out.println(p))
                .filter(p->p>300)
                .map(p -> p*(1-0.1))
                .sorted()
                .forEach(p-> System.out.println(p));

        System.out.println(prices);
    }

    public static void main(String[] args) {
        beforeJava8();
        streamLambdaJava8();
    }

//    Consumer:
//        T → void
//    Predicate:
//        T → boolean
//    Function:
//        T→R
//    Requires:
//        T implements Comparable
//    Consumer:(+ terminal operation)
//        T → void
//    Syntax:
//            (int x) -> { int y = x + 1; return y; }
//            (int x) -> x + 1
//            (x, y) -> x * y

}
