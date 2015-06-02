package com.hedwig.functionaljava.collections;


import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by patrick on 15/4/12.
 *
 * @version $Id$
 */
import static com.hedwig.functionaljava.collections.Folks.*;
import static java.util.stream.Collectors.joining;

public class PickAnElement {
    public static void main(String[] args) {
        final List<String> startWithN = friends.stream().filter(name -> name.startsWith("N"))
                .collect(Collectors.toList());
        System.out.println(startWithN);
        System.out.println(friends.stream().filter(name -> name.startsWith("N"))
                .count());
        Predicate<String> startN = name -> name.startsWith("N");
        System.out.println(friends.stream().filter(startN)
                .count());
        System.out.println(friends.stream()
                .map(String::toUpperCase)
                .collect(joining(", ")));

        List<String> transformedList = Lists.newLinkedList();
        friends.forEach(element -> transformedList.add(element.toUpperCase()));
        System.out.println(transformedList);

        friends.stream().map(name -> name.toLowerCase())
                .forEach(element -> {
                    transformedList.add(element);
                    System.out.println(transformedList);
                });
        System.out.println(transformedList);
    }
}
