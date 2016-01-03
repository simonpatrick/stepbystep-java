package io.hedwing.java8samples.streamexercise;

import com.google.common.collect.Sets;
import io.hedwing.java8samples.musicsample.Album;
import io.hedwing.java8samples.musicsample.Artist;
import io.hedwing.java8samples.musicsample.Track;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by patrick on 16/1/3.
 */
public class Refactor {
    public static boolean moreThan60(int length) {
        return length > 60 ? true : false;
    }

    public static interface LongTrackFinder {
        public Set<String> findLongTracks(List<Album> albums);
    }

    public static class Impl_BeforeJava8 implements LongTrackFinder {

        @Override
        public Set<String> findLongTracks(List<Album> albums) {
            Set<String> trackNames = Sets.newHashSet();
            for (Album album : albums) {
                for (Track track : album.getTrackList()) {
                    if (moreThan60(track.getLength())) {
                        trackNames.add(track.getName());
                    }
                }
            }

            return trackNames;
        }
    }

    public static class Impl_ForEach implements LongTrackFinder {

        @Override
        public Set<String> findLongTracks(List<Album> albums) {
            Set<String> trackNames = Sets.newHashSet();
            albums.stream().forEach(
                    album -> {
                        album.getTracks().forEach(track -> {
                            if (moreThan60(track.getLength())) {
                                trackNames.add(track.getName());
                            }
                        });
                    }
            );
            return trackNames;
        }
    }

    public static class Impl_FilterAndMap implements LongTrackFinder {

        @Override
        public Set<String> findLongTracks(List<Album> albums) {
            Set<String> trackNames = Sets.newHashSet();
            albums.stream().forEach(album -> {
                album.getTracks().filter(track ->
                                moreThan60(track.getLength())
                ).map(track -> track.getName()).forEach(name -> trackNames.add(name));
            });
            return trackNames;
        }
    }

    public static class ImplFlatMapFilterAndMap implements LongTrackFinder{

        @Override
        public Set<String> findLongTracks(List<Album> albums) {
            Set<String> trackNames = Sets.newHashSet();
            albums.stream()
                    .flatMap(album -> album.getTracks())
                    .filter(track -> moreThan60(track.getLength()))
                    .map(track -> track.getName())
                    .forEach(name -> trackNames.add(name));
            return trackNames;
        }
    }

    public static class Impl_Collector implements LongTrackFinder{

        @Override
        public Set<String> findLongTracks(List<Album> albums) {
            Set<String> trackNames = Sets.newHashSet();
            albums.stream()
                    .flatMap(album -> album.getTracks())
                    .filter(track -> moreThan60(track.getLength()))
                    .map(track -> track.getName()).collect(Collectors.toSet());
            return trackNames;
        }
    }

}
