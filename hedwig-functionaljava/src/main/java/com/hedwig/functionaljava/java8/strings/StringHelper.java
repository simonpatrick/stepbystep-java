package com.hedwig.functionaljava.java8.strings;

import com.hedwig.functionaljava.compare.Person;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by patrick on 15/6/3.
 *
 * @version $Id$
 */


public class StringHelper {

    public static <T extends CharSequence> void join(String delimiter,Iterable<T > list){
        System.out.println(String.join(delimiter, list));
    }

    public static void join(String delimiter,String...inputs){
        StringJoiner joiner = new StringJoiner(",");
        for (String input : inputs) {
            joiner.add(input);
        }

        System.out.println(joiner.toString());
    }


    public static void join_collector(){
        List<Person> list = Arrays.asList(
                new Person("John", 12),
                new Person("Anna", 21),
                new Person("Paul", 123)
        );

        String joinNames = list.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", "));
        System.out.println(joinNames);
    }


    public static void main(String[] args) {
        join_collector();
    }
}
