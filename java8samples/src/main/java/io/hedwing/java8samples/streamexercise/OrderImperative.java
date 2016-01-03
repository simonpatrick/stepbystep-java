package io.hedwing.java8samples.streamexercise;



import io.hedwing.java8samples.musicsample.Album;
import io.hedwing.java8samples.musicsample.Track;

import java.util.List;

public class OrderImperative extends Order {

    public OrderImperative(List<Album> albums) {
        super(albums);
    }

    // BEGIN body
public long countRunningTime() {
    long count = 0;
    for (Album album : albums) {
        for (Track track : album.getTrackList()) {
            count += track.getLength();
        }
    }
    return count;
}

public long countMusicians() {
    long count = 0;
    for (Album album : albums) {
        count += album.getMusicianList().size();
    }
    return count;
}

public long countTracks() {
    long count = 0;
    for (Album album : albums) {
        count += album.getTrackList().size();
    }
    return count;
}
    // END body

}
