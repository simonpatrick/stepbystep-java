package com.hedwig.stepbystep.javatutorial.jbehave.utils;

import com.gargoylesoftware.htmlunit.html.Util;

import java.io.IOException;
import java.util.Properties;

public class JavaUtils {

	public static String getValfromTestDataBundle(String key) {
		Properties p = new Properties();
		try {
			p.load(Util.class.getResourceAsStream("/testdata.properties"));
		} catch (IOException e) {
			System.err.println("Error in laoding the test data bundle: "
					+ e.getMessage());
			e.printStackTrace();
		}
		return p.getProperty(key);
	}

    public static void main(String[] args) {
        System.out.println(JavaUtils.getValfromTestDataBundle("country.selector.page.url"));
    }
}
