/*
 * Copyright (c) 2009-2014. 上海诺诺镑客 All rights reserved.
 * @(#) EnumValueTypeTest.java 2014-10-27 16:48
 */

package com.springdemo.learningMVC.data.src.test.java.com.data.jdbc.types;

import com.nonobank.common.base.Gender;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author fuchun
 * @version $Id: EnumValueTypeTest.java 291 2014-10-27 08:49:07Z fuchun $
 */
public class EnumValueTypeTest {

    private final EnumValueType<Gender, String> evt = new EnumValueType<>(
            Types.VARCHAR, Gender.class);

    @Test
    public void testGetValue() throws Exception {
        ResultSet rs = createMock(ResultSet.class);
        expect(rs.getObject(1)).andReturn(Gender.FEMALE.getValue());
        expect(rs.getString(1)).andReturn(Gender.FEMALE.getValue());
        replay(rs);

        Gender g = evt.getValue(rs, 1);
        assertNotNull(g);
        assertThat(g, Is.is(Gender.FEMALE));

        reset(rs);
        expect(rs.getObject(1)).andReturn("C");
        expect(rs.getString(1)).andReturn("C");
        replay(rs);

        g = evt.getValue(rs, 1);
        assertNull(g);
    }

    @Test
    public void testSetValue() throws Exception {
        PreparedStatement ps = createNiceMock(PreparedStatement.class);
        ps.setString(1, Gender.UNKNOWN.getValue());
        replay(ps);

        evt.setValue(ps, 1, Gender.UNKNOWN);
        verify(ps);

        reset(ps);
        ps.setNull(1, Types.VARCHAR);
        replay(ps);

        evt.setValue(ps, 1, null);
        verify(ps);
    }
}