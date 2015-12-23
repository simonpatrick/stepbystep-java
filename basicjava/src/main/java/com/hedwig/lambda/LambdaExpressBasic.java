package com.hedwig.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by patrick on 15/11/15.
 */
public class LambdaExpressBasic {

    public static void compareWithAnonymousImplementation(){
        List<String> lists = Arrays.asList("Say","Hello","Lambdas");
        System.out.println("before sorted "+lists);
        Collections.sort(lists, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println("after sorted "+lists);
    }

    public static void compareInJava8(){
        List<String> lists = Arrays.asList("Say","Hello","Lambdas");
        System.out.println("before sorted "+lists);
        Collections.sort(lists, (o1, o2) -> o1.compareTo(o2));
        lists.sort((o1,o2)->o1.compareTo(o2));

        System.out.println("after sorted "+lists);
    }

    public static void changeEveryItem(){
        List<String> lists = Arrays.asList("Say","Hello","Lambdas");
        lists.forEach(item-> System.out.println(item+" abcd"));
    }

    public static void main(String[] args) {
        compareWithAnonymousImplementation();
        compareInJava8();
        changeEveryItem();

        //used for FunctionInterface:Single Abstract Method
    }
}
