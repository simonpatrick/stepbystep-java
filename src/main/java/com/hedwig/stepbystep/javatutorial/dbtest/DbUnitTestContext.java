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

package com.hedwig.stepbystep.javatutorial.dbtest;

import com.hedwig.stepbystep.javatutorial.dbtest.dataset.DataSetLoader;
import com.hedwig.stepbystep.javatutorial.dbtest.dboperation.DatabaseOperationLookup;
import org.dbunit.database.IDatabaseConnection;

import java.lang.reflect.Method;

/**
 * Provides context for the {@link DbUnitRunner}.
 *
 * @author Phillip Webb
 */
interface DbUnitTestContext {

	/**
	 * Returns the {@link org.dbunit.database.IDatabaseConnection} that should be used when performing database setup and teardown.
	 * @return The connection
	 */
	IDatabaseConnection getConnection();

	/**
	 * Returns the {@link DataSetLoader} that should be used to load {@link org.dbunit.dataset.IDataSet}s.
	 * @return The dataset loader
	 */
	DataSetLoader getDataSetLoader();

	/**
	 * Returns the {@link DatabaseOperationLookup} that should be used to lookup database operations.
	 * @return the database operation lookup
	 */
	DatabaseOperationLookup getDatbaseOperationLookup();

	/**
	 * Returns the class that is under test.
	 * @return The class under test
	 */
	Class<?> getTestClass();

	/**
	 * Returns the method that is under test.
	 * @return The method under test
	 */
	Method getTestMethod();

	/**
	 * Returns any exception that was thrown during the test or <tt>null</tt> if no test exception occurred.
	 * @return the test exception or <tt>null</tt>
	 */
	Throwable getTestException();
}
