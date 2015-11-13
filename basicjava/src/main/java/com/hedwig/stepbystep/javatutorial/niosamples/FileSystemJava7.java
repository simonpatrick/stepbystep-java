package com.hedwig.stepbystep.javatutorial.niosamples;

import com.sun.nio.zipfs.ZipFileSystem;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

/**
 * Created by patrick on 15/11/13.
 */
public class FileSystemJava7 {

    @Test
    public void testGetPath(){
        String path="/Users/patrick";
        Path nPath = Paths.get(path);
        Path defaultPath = FileSystems.getDefault().getPath(path);
        Path byURI = Paths.get(URI.create("file:///Users/patrick/workspace"));
        System.out.println(nPath);  // the path is /pages
        System.out.println(defaultPath);  // the path is /pages
        System.out.println(byURI);  // the path is /pages
        System.out.println(nPath.getFileName());
        System.out.println(nPath.getName(0));
        System.out.println(nPath.getNameCount());
        System.out.println(nPath.subpath(0, 1));
    }

    @Test
    public void testJarFile() throws URISyntaxException, IOException {
//        final URI jarFileURI = URI.create("jar:file"+file.toUri().getPath());
        URI url = ClassLoader.getSystemClassLoader().getResource("org/testng").toURI();
//        Path p = Paths.get("testng-6.8.8.jar");
        FileSystem fs = FileSystems.newFileSystem(url, Collections.emptyMap());
        final Path jarPath = fs.getPath("/");

//        System.out.println(url.getProtocol());
        String classPath = ClassLoader.getSystemClassLoader().getResource("").getPath();
        System.out.println(classPath);
        Path target = Paths.get(classPath,"copied");

        Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {

            private Path currentTarget;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                currentTarget = target.resolve(jarPath.relativize(dir).toString());
                Files.createDirectories(currentTarget);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(jarPath.relativize(file).toString()),StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

        });
//        Path p = Paths.get(url.toURI());
//
//
//        System.out.println(p.normalize());
    }
}
