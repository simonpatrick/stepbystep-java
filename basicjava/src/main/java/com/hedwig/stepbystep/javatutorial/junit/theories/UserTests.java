package com.hedwig.stepbystep.javatutorial.junit.theories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * http://web.archive.org/web/20110608210825/http://shareandenjoy.saff.net/tdd-specifications.pdf
 * http://web.archive.org/web/20071012143326/popper.tigris.org/tutorial.html
 */
@RunWith(Theories.class)
public class UserTests {
    private static final Logger logger = LogManager.getLogger(UserTests.class.getName());

    @DataPoint
    public static final String GOOD_USERNAME= "optimus";
    @DataPoint
    public static final String USERNAME_WITH_SLASH = "optimus/prime";

    @Theory
    public void filenameIncludesUsername(String username) {
        assumeThat(username, not(containsString("/")));
        assertThat(new User(username).configFileName(), containsString(username));
    }

    public class User{
        private String username;

        public User(String username) {
            this.username = username;
        }

        public String configFileName(){
            return this.username;
        }
    }
}
