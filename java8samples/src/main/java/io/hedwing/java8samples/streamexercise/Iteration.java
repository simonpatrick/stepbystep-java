package io.hedwing.java8samples.streamexercise;

import io.hedwing.java8samples.musicsample.Artist;

import java.util.Iterator;
import java.util.List;

/**
 * Created by patrick on 16/1/3.
 */
public class Iteration {
    public int externalCountArtist(List<Artist> artistList) {
        int count = 0;
        for (Artist artist : artistList) {
            if (artist.isFrom("London")) {
                count++;
            }
        }

        return count;
    }

    public int countByIterator(List<Artist> artists) {
        int count = 0;
        Iterator<Artist> iterator = artists.iterator();
        while (iterator.hasNext()) {
            Artist artist = iterator.next();
            if (artist.isFrom("London")) count++;
        }

        return count;
    }

    public long internalCountArtist(List<Artist> artists) {
        return artists.stream().filter(artist -> artist.isFrom("London")).count();
    }

    public void filterArtist(List<Artist> artists) {
        artists.stream().filter(artist -> artist.isFrom("London"));
    }


}
