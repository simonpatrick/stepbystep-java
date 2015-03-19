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

import com.hedwig.stepbystep.javatutorial.dbtest.dataset.DataSetLoader;
import com.hedwig.stepbystep.javatutorial.dbtest.dataset.FlatXmlDataSetLoader;
import com.hedwig.stepbystep.javatutorial.dbtest.dboperation.DatabaseOperationLookup;
import com.hedwig.stepbystep.javatutorial.dbtest.dboperation.DefaultDatabaseOperationLookup;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

import java.lang.annotation.*;

/**
 * Annotation that can be used to configure {@link DbUnitTestExecutionListener}.
 *
 * @see DbUnitTestExecutionListener
 * @author Phillip Webb
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface DbUnitConfiguration {

	/**
	 * Returns the name of a spring bean will be used to access a {@link IDatabaseConnection}. The referenced bean can
	 * either be a instance of {@link IDatabaseConnection} or a {@link javax.sql.DataSource}.
	 * @return the bean name of the database connection
	 */
	String databaseConnection() default "";

	/**
	 * Returns the class that will be used to load {@link IDataSet} resources. The specified class must implement
	 * {@link DataSetLoader} and must have a default constructor.
	 * @return the data set loader class
	 */
	Class<? extends DataSetLoader> dataSetLoader() default FlatXmlDataSetLoader.class;

	/**
	 * Returns the name of the bean that will be used to load {@link IDataSet} resources. The specified bean must
	 * implement {@link DataSetLoader}.
	 * @return the data set loader bean name
	 */
	String dataSetLoaderBean() default "";

	/**
	 * Returns the class that will be used to lookup DBUnit databse operations. The specific class must implement
	 * {@link DatabaseOperationLookup} and must have a default constructor.
	 * @return the database operation lookup
	 */
	Class<? extends DatabaseOperationLookup> databaseOperationLookup() default DefaultDatabaseOperationLookup.class;

}
