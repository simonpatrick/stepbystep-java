package com.hedwig.stepbystep.javatutorial.junit.assumptions;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

public class AssumptionsTests {

    @Test
    public void filenameIncludeUserName(){
       assumeThat(String.valueOf(File.separatorChar),is("\\"));
        //assertThat(new User("optimus").configFileName(), is("configfiles/optimus.cfg"));
    }

}
