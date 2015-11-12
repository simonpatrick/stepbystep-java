package com.hedwig.stepbystep.javatutorial.junit.catogories;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(CategoriesTest.SlowTests.class)
@Suite.SuiteClasses({CategoriesTest.A.class,CategoriesTest.B.class})
public class SlowTestSuite {
}
