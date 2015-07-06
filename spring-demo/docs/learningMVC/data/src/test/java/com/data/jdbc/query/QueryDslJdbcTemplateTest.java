/*
 * Copyright (c) 2009-2014. 上海诺诺镑客 All rights reserved.
 * @(#) QueryDslJdbcTemplateTest.java 2014-10-27 16:48
 */

package com.springdemo.learningMVC.data.src.test.java.com.data.jdbc.query;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.BeanMapper;
import com.mysema.query.types.Projections;
import com.nonobank.common.base.propertyeditors.EnumValuePropertyEditor;
import com.nonobank.data.convert.spring.TimestampToDateTimeConverter;
import com.nonobank.data.domain.TUserStatus;
import com.nonobank.data.domain.TestDomain;
import com.nonobank.data.jdbc.types.TUserStatusType;
import com.nonobank.data.repository.support.QueryDsl;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyEditor;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import static com.nonobank.data.domain.QTestDomain.qTestDomain;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

/**
 * {@code QueryDslJdbcTemplate} test case.
 *
 * @author fuchun
 * @version $Id: QueryDslJdbcTemplateTest.java 291 2014-10-27 08:49:07Z fuchun $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-context.xml")
@DirtiesContext
public class QueryDslJdbcTemplateTest {

    @Resource
    private DataSource dataSource;

    @Resource
    private ConversionService conversionService;

    private QueryDslJdbcTemplate template;

    @Before
    public void setUp() throws Exception {
        template = new QueryDslJdbcTemplate(dataSource);

        String statusName = ColumnMetadata.getName(qTestDomain.status);
        template.registerColumnType(
                qTestDomain.getTableName(), statusName,
                new TUserStatusType());
    }

    @Test
    public void testCount() throws Exception {
        Long count = template.count(template.newSqlQuery().from(qTestDomain));

        assertNotNull(count);
        assertThat(count, Is.is(2L));
    }

    @Test
    public void testCountDistinct() throws Exception {
        Long count = template.countDistinct(
                template.newSqlQuery().from(qTestDomain)
                        .where(qTestDomain.name.isNotNull()));

        assertNotNull(count);
        assertThat(count, Is.is(2L));
    }

    @Test
    public void testExists() throws Exception {
        boolean exists = template.exists(
                template.newSqlQuery().from(qTestDomain)
                        .where(qTestDomain.name.isNotNull()));
        assertTrue(exists);
    }

    @Test
    public void testNotExists() throws Exception {
        boolean notExists = template.notExists(
                template.newSqlQuery().from(qTestDomain)
                        .where(qTestDomain.name.isNull()));
        assertTrue(notExists);
    }

    @Test
    public void testQuery() throws Exception {
        SQLQuery sqlQuery = template.newSqlQuery()
                .from(qTestDomain)
                .where(qTestDomain.name.eq("Tomas"));

        JavaBeanPropertyRowMapper<TestDomain> tdRowMapper = JavaBeanPropertyRowMapper
                .newInstance(TestDomain.class);
        Table<String, Class<?>, PropertyEditor> editorTable = HashBasedTable.create();
        editorTable.put("status", TUserStatus.class,
                new EnumValuePropertyEditor<>(TUserStatus.class));
        tdRowMapper.setCustomPropertyEditors(editorTable);
        tdRowMapper.setConversionService(conversionService);

        TestDomain td = template.queryForObject(sqlQuery,
                tdRowMapper,
                qTestDomain.all());

        assertNotNull(td);

        sqlQuery = template.newSqlQuery()
                .from(qTestDomain).where(qTestDomain.name.isNotNull());

        try {
            td = template.queryForObject(sqlQuery,
                    tdRowMapper,
                    qTestDomain.all());
        } catch (Exception ex) {
            td = null;
            assertThat(ex, instanceOf(IncorrectResultSizeDataAccessException.class));
        }

        assertNull(td);

        List<TestDomain> list = template.query(sqlQuery,
                tdRowMapper, qTestDomain.all());

        assertThat(list, IsNull.notNullValue());
        assertEquals(list.size(), 2);
    }

    @Test
    public void testQuery1() throws Exception {
        SQLQuery sqlQuery = template.newSqlQuery()
                .from(qTestDomain)
                .where(qTestDomain.name.eq("Tomas"));

        Converter<Timestamp, DateTime> tdt  =TimestampToDateTimeConverter.INSTANCE;
        ResultSetExtractor<TestDomain> rse = rs -> {
            if (rs.next()) {
                TestDomain td1 = new TestDomain();
                td1.setId(rs.getInt("ID"));
                td1.setName(rs.getString("NAME"));
                td1.setStatus(TUserStatus.of(rs.getInt("STATUS")));

                td1.setCreatedDate(tdt
                        .convert(rs.getTimestamp("CREATED_DATE")));
                td1.setLastModifiedDate(tdt
                        .convert(rs.getTimestamp("LAST_MODIFIED_DATE")));
                return td1;
            }
            return null;
        };

        TestDomain td = template.queryForObject(sqlQuery, rse, qTestDomain.all());

        assertThat(td, IsNull.notNullValue());

        sqlQuery = template.newSqlQuery()
                .from(qTestDomain)
                .where(qTestDomain.name.isNotNull());
        List<TestDomain> list = template.query(sqlQuery,
                (ResultSet rs) -> {
                    List<TestDomain> l = Lists.newArrayList();
                    int i = 0;
                    while (rs.next()) {
                        TestDomain td1 = new TestDomain();
                        td1.setId(rs.getInt("ID"));
                        td1.setName(rs.getString("NAME"));
                        td1.setStatus(TUserStatus.of(rs.getInt("STATUS")));

                        td1.setCreatedDate(tdt
                                .convert(rs.getTimestamp("CREATED_DATE")));
                        td1.setLastModifiedDate(tdt
                                .convert(rs.getTimestamp("LAST_MODIFIED_DATE")));
                        l.add(i++, td1);
                    }
                    return l;
        }, qTestDomain.all());

        assertThat(list, IsNull.notNullValue());
        assertEquals(list.size(), 2);
    }

    @Test
    public void testQuery2() throws Exception {

        SQLQuery sqlQuery = template.newSqlQuery()
                .from(qTestDomain)
                .where(qTestDomain.name.eq("Tomas"));

        TestDomain td = template.queryForObject(sqlQuery,
                Projections.bean(qTestDomain, QueryDsl.columns(qTestDomain)));

        assertNotNull(td);

        List<TestDomain> list = template.query(sqlQuery,
                Projections.bean(qTestDomain, QueryDsl.columns(qTestDomain)));

        assertThat(list, IsNull.notNullValue());
        assertEquals(list.size(), 1);
    }

    @Test
    public void testInsert() throws Exception {
        TestDomain td = TestDomain
                .newInstance("President", TUserStatus.VALIDATED)
                .id(1001);
        long row = template.insert(qTestDomain, insert ->
                insert.populate(td, BeanMapper.WITH_NULL_BINDINGS)
                        .execute());
        assertEquals(row, 1);

        TestDomain td2 = template.queryForObject(
                template.newSqlQuery().from(qTestDomain)
                .where(qTestDomain.id.eq(td.getId())),
                Projections.bean(qTestDomain, QueryDsl.columns(qTestDomain)));
        assertNotNull(td2);
        assertEquals(td, td2);

        // 还原测试数据
        template.delete(qTestDomain, delete ->
                delete.where(qTestDomain.id.eq(td.getId())).execute());
    }

    @Test
    public void testInsertWithKey() throws Exception {
        TestDomain td = TestDomain
                .newInstance("President", TUserStatus.VALIDATED);

        Integer newId = template.insertWithKey(qTestDomain, insert ->
            insert.populate(td, BeanMapper.WITH_NULL_BINDINGS)
                    .executeWithKey(qTestDomain.id));
        td.setId(newId);

        assertThat(newId, IsNull.notNullValue());

        // 还原测试数据
        template.delete(qTestDomain, delete ->
                delete.where(qTestDomain.id.eq(newId)).execute());
    }

    @Test
    public void testUpdate() throws Exception {
        TestDomain td = TestDomain
                .newInstance("President", TUserStatus.VALIDATED);

        Integer newId = template.insertWithKey(qTestDomain, insert ->
                insert.populate(td, BeanMapper.WITH_NULL_BINDINGS)
                        .executeWithKey(qTestDomain.id));
        td.setId(newId);

        assertThat(newId, IsNull.notNullValue());

        String newName = "White";
        long row = template.update(qTestDomain, update ->
                update.set(qTestDomain.name, newName)
                        .where(qTestDomain.id.eq(newId))
                        .execute());

        assertTrue(row > 0);

        // 还原测试数据
        template.delete(qTestDomain, delete ->
                delete.where(qTestDomain.id.eq(newId)).execute());
    }
}