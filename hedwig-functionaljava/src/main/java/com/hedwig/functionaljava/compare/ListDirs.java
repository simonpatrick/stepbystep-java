package com.hedwig.functionaljava.compare;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by patrick on 15/4/15.
 *
 * @version $Id$
 */


public class ListDirs {
    public static void main(String[] args) throws IOException {
        Files.list(Paths.get("")).
                filter(Files::isDirectory)
                .forEach(System.out::println);

        //list files
        Files.list(Paths.get("")).filter(element -> !Files.isDirectory(element))
                .forEach(System.out::println);

        final File[] files = new File(".").listFiles(file -> file.isHidden());
        new File(".").listFiles(File::isHidden);
        Arrays.stream(files).forEach(System.out::println);

        File file = new File("main");
        System.out.println(file.getAbsoluteFile());
        final String[] files1 =
                new File("main").list(new java.io.FilenameFilter() {
                    public boolean accept(final File dir, final String name) {
                        return name.endsWith(".java");
                    }
                });

//        Files.newDirectoryStream(
//                Paths.get("/src/main/java/com/hedwig/functionaljava/baisc"), path -> path.toString().endsWith(".java"))
//                .forEach(System.out::println);
    }

}
