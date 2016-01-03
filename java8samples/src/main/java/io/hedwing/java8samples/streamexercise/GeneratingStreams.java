package io.hedwing.java8samples.streamexercise;

import java.util.stream.Stream;

/**
 * Created by patrick on 16/1/3.
 */
public class GeneratingStreams {
    static void generate(){
        Stream.generate(()->"Hello World")
                .limit(3)
                .forEach(System.out::println);
    }

    static void iterate(){
        Stream.iterate(0,x->x+1)
                .limit(5)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        iterate();
        generate();
    }
}
