package io.hedwing.java8samples.streamexercise;

import com.google.common.collect.Lists;
import io.hedwing.java8samples.musicsample.Artist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by patrick on 16/1/3.
 */
public class StreamExercises {

    public static int countBandMembers(List<Artist> artists) {
        int totalMembers = 0;
        for (Artist artist : artists) {
            Stream<Artist> members = artist.getMembers();
            totalMembers += members.count();

        }
        return totalMembers;
    }
    //todo understand reduce
    public static <T, R> List<R> map(Stream<T> stream, Function<T, R> mapper) {
        return stream.reduce(new ArrayList<>(), (acc, value) -> {
            // Make copy of list (modifying acc would violate contract of reduce method)
            ArrayList<R> result = new ArrayList<>();
            result.addAll(acc);
            result.add(mapper.apply(value));
            return result;
        }, (left, right) -> {
            ArrayList<R> result = new ArrayList<>();
            result.addAll(left);
            result.addAll(right);
            return result;
        });
    }
}
