package io.hedwing.java8samples.streamexercise;


import io.hedwing.java8samples.musicsample.Album;

import java.util.IntSummaryStatistics;

public class Primitives {

    // BEGIN printTrackLengthStatistics
    public static void printTrackLengthStatistics(Album album) {
        IntSummaryStatistics trackLengthStats
                = album.getTracks()
                .mapToInt(track -> track.getLength())
                .summaryStatistics();
        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                trackLengthStats.getMax(),
                trackLengthStats.getMin(),
                trackLengthStats.getAverage(),
                trackLengthStats.getSum());
    }

}
