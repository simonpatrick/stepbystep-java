package com.springdemo.bdtest;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;


/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(value = "classpath:spring-datasource.xml")
@Sql(value = {"classpath:schema.sql","classpath:init-data.sql"
        ,"classpath:update-data.sql"},
        config = @SqlConfig(encoding = "utf-8",
                dataSource = "datasource1"
                ,transactionManager = "txManager1"))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClassLevelTest {
    private JdbcTemplate template;

    @Autowired
    @Qualifier("datasource1")
    public void setDataSource1(DataSource datasource1){
        this.template=new JdbcTemplate(datasource1);
    }

    @Test
    public void test01_simple() {
        Assert.assertEquals(
                Integer.valueOf(3),
                template.queryForObject("select count(1) from users", Integer.class));
    }

}
