package com.hedwig.stepbystep.javatutorial.java8;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LambadaLearning {

    public static void sortSample(){ //original sort
        List<Integer> names = Lists.newArrayList(12,34,454,554,443);
        Collections.sort(names, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        System.out.println(names);
    }

    public static void sortLambada(){//java8
        List<Integer> names = Lists.newArrayList(12,34,454,554,443);
        Collections.sort(names,(o1,o2)->o1-o2);
        System.out.println(names);
    }

    public static void covertStream(){
        List<String> names = Lists.newArrayList("ABCd","ddnDDKK");
        // map means every iterator
        List<String> lowercaseName = names.stream().map((String name)->{
            return name.toLowerCase();
        }).collect(Collectors.toList());
        System.out.println(lowercaseName);

    }

    public static void covertStreamS(){
        List<String> names = Lists.newArrayList("ABCd","ddnDDKK");
        System.out.println(names.stream().map(name->name.toUpperCase())
                .collect(Collectors.toList()));
    }

    //Method Reference
    public static void covertMethodReference(){
        List<String> names = Lists.newArrayList("ABCd","ddnDDKK");
        System.out.println(names.stream().map(String::toUpperCase)
                .collect(Collectors.toList()));
    }

    public static void guavaSample(){
        List<String> names = Lists.newArrayList("ABCd","ddnDDKK");
        // map means every iterator
        List<String> lowercaseName = FluentIterable
                .from(names)
                .transform(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.toUpperCase();
            }

            @Override
            public boolean equals(Object o) {
                return this.equals(0);
            }
        }).toList();
        System.out.println(lowercaseName);
    }

    public static void main(String[] args) {
        sortSample();
        sortLambada();
        covertStream();
        guavaSample();
        covertStreamS();
        covertMethodReference();
    }
}
