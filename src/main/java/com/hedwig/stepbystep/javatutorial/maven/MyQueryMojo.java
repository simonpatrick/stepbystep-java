package com.hedwig.stepbystep.javatutorial.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

public class MyQueryMojo
        extends AbstractMojo {
    /**
     * @parameter expression="${query.url}"
     */
    private String url;

    /**
     * @parameter default-value="60"
     */
    private int timeout;

    /**
     * @parameter
     */
    private String[] options;

    public void execute()
            throws MojoExecutionException {
        System.out.println("Query Plugin is executed");
    }
}