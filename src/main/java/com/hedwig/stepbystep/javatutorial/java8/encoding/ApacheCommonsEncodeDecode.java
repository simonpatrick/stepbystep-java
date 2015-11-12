package com.hedwig.stepbystep.javatutorial.java8.encoding;

import org.apache.commons.codec.binary.Base64;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/11/12.
 */
public class ApacheCommonsEncodeDecode {

    @Test
    public void whenStringIsEncoded(){
        final String originalInput = "test input";
        final Base64 base64= new Base64();
        final String encodingString = new String(base64.encode(originalInput.getBytes()));
        System.out.println(encodingString);
        assertNotNull(encodingString);
        assertNotEquals(encodingString, originalInput);
    }

    @Test
    public void whenStringIsEncodedUsingStaticMethod() {
        final String originalInput = "test input";
        final String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));
        System.out.println(encodedString);
        assertNotNull(encodedString);
        assertNotEquals(originalInput, encodedString);
    }

}
