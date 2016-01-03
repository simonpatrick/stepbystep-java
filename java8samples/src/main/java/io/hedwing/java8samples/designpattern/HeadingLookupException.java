package io.hedwing.java8samples.designpattern;

import java.io.IOException;

public class HeadingLookupException extends RuntimeException {
    public HeadingLookupException(IOException e) {
    }
}
