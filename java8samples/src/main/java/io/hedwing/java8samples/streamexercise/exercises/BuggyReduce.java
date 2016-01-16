package io.hedwing.java8samples.streamexercise.exercises;

import java.util.List;

public class BuggyReduce {

    /*
    Buggy Version:
    // BEGIN buggyMultiplyThrough
public static int multiplyThrough(List<Integer> linkedListOfNumbers) {
    return linkedListOfNumbers.stream()
                  .reduce(5, (acc, x) -> x * acc);
}
    // END buggyMultiplyThrough
    */

    public static int multiplyThrough(List<Integer> numbers) {
        return 5 * numbers.parallelStream()
                          .reduce(1, (acc, x) -> x * acc);
    }

}