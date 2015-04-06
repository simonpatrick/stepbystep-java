package com.springdemo.learningMVC.data.src.test.java.com.data.support;


import com.common.base.RandomStrings;
import com.data.domain.TUserStatus;
import com.data.domain.TestDomain;
import com.data.repository.support.JvmMemoryQuery;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class JvmMemoryQueryTest {

    static final List<TestDomain> dataSource = new ArrayList<>(10000);

    private JvmMemoryQuery<TestDomain, Integer> jvmMemoryQuery;

    @BeforeClass
    public static void initialize() {
        for (int i = 0; i < 100000; i++) {
            int status = Integer.valueOf(i % 3).compareTo(1);
            TestDomain td = TestDomain.newInstance(
                    RandomStrings.randomAlphabetic(8), TUserStatus.of(status))
                    .id((i + 1) * 10);
            dataSource.add(i, td);
        }
    }

    @Before
    public void setUp() throws Exception {
        jvmMemoryQuery = new JvmMemoryQuery<TestDomain, Integer>(100);
        jvmMemoryQuery.importData(dataSource);
    }

    @Test
    public void testFindAll() throws Exception {
        // sort by name DESC
        List<TestDomain> tdList = jvmMemoryQuery.findAll(
                td -> td.getStatus() == TUserStatus.VALIDATED,
                new Sort(Sort.Direction.DESC, TestDomain.PROP_NAME));

        // default sort by id ASC
        List<TestDomain> noSortTdList = jvmMemoryQuery.findAll(
                td -> td.getStatus() == TUserStatus.VALIDATED, (Sort) null);

        assertThat(tdList, notNullValue());
        assertThat(noSortTdList, notNullValue());
        assertThat(tdList, IsNot.not(noSortTdList));
    }
}