package io.hedwing.java8samples.streamexercise;


import io.hedwing.java8samples.musicsample.Album;
import io.hedwing.java8samples.musicsample.Artist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.hedwing.java8samples.streamexercise.CollectorExamples.countWords;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.groupingBy;

public class MethodReferences {

    public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician));
    }

    private static final Pattern SPACES = Pattern.compile("\\w+");

    public static Map<String, Long> countWordsIn(Path path) throws IOException {
        Stream<String> words = Files.readAllLines(path, defaultCharset())
                .stream()
                .flatMap(SPACES::splitAsStream);

        return countWords(words);
    }

}
