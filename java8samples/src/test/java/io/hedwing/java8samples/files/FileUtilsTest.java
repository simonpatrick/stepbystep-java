package io.hedwing.java8samples.files;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Created by patrick on 16/1/6.
 */
public class FileUtilsTest {

    @Test
    public void testFindMapperXML() throws Exception {

    }

    @Test
    public void testIsDir(){
       assertTrue(new FileUtils().isDir());
    }

    @Test
    public void testRecordViolations(){
        new  FileUtils().recordViolations();
    }
}