/*
 * Copyright 2002-2013 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hedwig.stepbystep.javatutorial.dbtest.annotation;


import com.hedwig.stepbystep.javatutorial.dbtest.assertion.DatabaseAssertionMode;

import java.lang.annotation.*;

/**
 * Test annotation that can be used to assert that a database is in given state after tests have run.
 *
 * @see DbUnitTestExecutionListener
 *
 * @author Phillip Webb
 * @author Mario Zagar
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ExpectedDatabase {

	/**
	 * Provides the location of the dataset that will be used to test the database.
	 * @return The dataset locations
	 * @see DbUnitConfiguration#dataSetLoader()
	 */
	String value();

	/**
	 * Database assertion mode to use. Default is {@link DatabaseAssertionMode#DEFAULT}.
	 * @return Database assertion mode to use
	 */
	DatabaseAssertionMode assertionMode() default DatabaseAssertionMode.DEFAULT;

	/**
	 * Optional table name that can be used to limit the comparison to a specific table.
	 * @return the table name
	 */
	String table() default "";

	/**
	 * Optional SQL to retrieve the actual subset of the table rows from the database. NOTE: a {@link #table() table
	 * name} must also be specified when using a query.
	 * @return the SQL Query
	 */
	String query() default "";

	/**
	 * If this expectation overrides any others that have been defined at a higher level. Defaults to {@code true}
	 * @return if this annotation overrides any others
	 */
	boolean override() default true;

}
