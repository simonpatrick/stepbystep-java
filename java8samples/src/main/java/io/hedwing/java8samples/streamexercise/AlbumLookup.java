package io.hedwing.java8samples.streamexercise;


import io.hedwing.java8samples.musicsample.Album;

public interface AlbumLookup {
    Album lookupByName(String albumName);
}
