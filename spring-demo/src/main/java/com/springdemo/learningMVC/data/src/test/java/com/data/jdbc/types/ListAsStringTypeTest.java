/*
 * Copyright (c) 2009-2014. 上海诺诺镑客 All rights reserved.
 * @(#) ListAsStringTypeTest.java 2014-10-27 16:48
 */

package com.springdemo.learningMVC.data.src.test.java.com.data.jdbc.types;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.easymock.EasyMock;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * @author fuchun
 * @version $Id: ListAsStringTypeTest.java 291 2014-10-27 08:49:07Z fuchun $
 */
public class ListAsStringTypeTest {

    private ResultSet rs;
    private PreparedStatement ps;

    private void setUpMockResultSet(int index, String value) throws Exception {
        rs.getString(index);
        EasyMock.expectLastCall().andReturn(value);
        EasyMock.replay(rs);
    }

    private <T> void setUpMockPreparedStatement(int index, List<T> list) throws Exception {
        if (list == null || list.isEmpty()) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, Joiner.on(",").skipNulls().join(list));
        }
    }

    @Before
    public void setUp() throws Exception {
        rs = EasyMock.createMock(ResultSet.class);
        ps = EasyMock.createMock(PreparedStatement.class);
    }

    @After
    public void tearDown() throws Exception {
        EasyMock.reset(rs);
        EasyMock.reset(ps);
    }

    @Test
    public void testGetValue() throws Exception {
        int index = 1;
        String str = "1,2,4,8,16";
        setUpMockResultSet(index, str);

        ListAsStringType<Integer> asType =
                new ListAsStringType<>(Ints.stringConverter());
        List<Integer> intValues = asType.getValue(rs, index);

        assertThat(intValues, IsNull.notNullValue());
        assertThat(intValues, Is.is(Lists.transform(
                Splitter.on(",").splitToList(str), Ints.stringConverter())));
    }

    @Test
    public void testSetValue() throws Exception {
        int index = 1;
        List<Long> userIds = ImmutableList.of(10002L, 10007L, 11879L, 12991L);
        setUpMockPreparedStatement(index, userIds);

        ps.executeQuery("select * from test_db");
        EasyMock.expectLastCall().andReturn(rs);
        rs.getString(index);
        EasyMock.expectLastCall().andReturn(Joiner.on(",").join(userIds));
        EasyMock.replay(ps, rs);

        ListAsStringType<Long> asType =
                new ListAsStringType<>(Longs.stringConverter());
        asType.setValue(ps, index, userIds);

        ResultSet rs1 = ps.executeQuery("select * from test_db");

        assertThat(rs1, IsNull.notNullValue());
        assertThat(rs1.getString(index), Is.is(Joiner.on(",").join(userIds)));
    }
}