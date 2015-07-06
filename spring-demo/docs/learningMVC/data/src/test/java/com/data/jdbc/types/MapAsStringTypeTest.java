/*
 * Copyright (c) 2009-2014. 上海诺诺镑客 All rights reserved.
 * @(#) MapAsStringTypeTest.java 2014-10-27 16:48
 */

package com.springdemo.learningMVC.data.src.test.java.com.data.jdbc.types;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.easymock.EasyMock;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author fuchun
 * @version $Id: MapAsStringTypeTest.java 291 2014-10-27 08:49:07Z fuchun $
 */
public class MapAsStringTypeTest {

    private final MapAsStringType mst = new MapAsStringType();

    @Test
    public void testGetReturnClass() throws Exception {
        Class<Map<String, String>> clazz = mst.getReturnedClass();

        assertNotNull(clazz);
    }

    @Test
    public void testGetValue() throws Exception {
        ResultSet rs = createMock(ResultSet.class);
        expect(rs.getString(0)).andReturn(null);
        replay(rs);

        Map<String, String> map = mst.getValue(rs, 0);
        assertThat(map, IsNull.nullValue());

        reset(rs);
        expect(rs.getString(0)).andReturn("1:one,2:second");
        replay(rs);

        map = mst.getValue(rs, 0);
        assertNotNull(map);
        assertTrue(map.size() == 2);

        reset(rs);
        expect(rs.getString(0)).andReturn("    ");
        replay(rs);

        map = mst.getValue(rs, 0);
        assertNull(map);
    }

    @Test
    public void testSetValue() throws Exception {
        PreparedStatement ps = createNiceMock(PreparedStatement.class);
        ps.setNull(0, Types.VARCHAR);
        replay(ps);

        Map<String, String> map = ImmutableMap.of();
        mst.setValue(ps, 0, map);
        EasyMock.verify(ps);

        reset(ps);
        map = ImmutableMap.of("k1", "v1");
        ps.setString(0, Joiner.on(',').withKeyValueSeparator(":").join(map));
        replay(ps);

        mst.setValue(ps, 0, map);
        EasyMock.verify(ps);
    }
}