/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package io.hedwing.java8samples.musicsample;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MusicChapter {

    protected final List<Artist> artists;
    protected final List<Album> albums;

    public MusicChapter() {
        artists = SampleData.getThreeArtists();
        albums = SampleData.albumsList;
    }


    public List<String> getNamesOfArtists() {
        return artists.stream().map(Artist::getName)
                .collect(Collectors.toList());
    }

    public List<Artist> artistsByLocation(String location) {
        return artists.stream().filter(artist -> location.equalsIgnoreCase(
                artist.getNationality()
        )).collect(Collectors.toList());
    }

}
