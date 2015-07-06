/*
 * Copyright (c) 2009-2014. 上海诺诺镑客 All rights reserved.
 * @(#) JdbcTestDomainRepository.java 2014-11-11 12:53
 */

package com.springdemo.learningMVC.data.src.test.java.com.data.jdbc;

import com.data.domain.TestDomain;
import com.data.domain.TestDomainRepository;
import com.data.qdomain.jdbc.QueryDslJdbcTemplate;
import com.data.repository.support.QueryDslJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static com.data.domain.QTestDomain.qtd;

/**
 * @author fuchun
 * @version $Id: JdbcTestDomainRepository.java 508 2014-11-20 06:48:13Z fuchun $
 * @since 2.0
 */
@Repository("testDomainRepository")
public class JdbcTestDomainRepository extends QueryDslJdbcRepository<TestDomain, Integer>
        implements TestDomainRepository {

    public JdbcTestDomainRepository() {
        super(qtd);
    }

    @Override
    public TestDomain findByName(String name) {
        return findOne(qtd.name.eq(name));
    }

    @Override
    @Autowired(required = false)
    @Qualifier("ds2QueryDslJdbcTemplate")
    public void setQueryDslJdbcTemplate(QueryDslJdbcTemplate queryDslJdbcTemplate) {
        super.setQueryDslJdbcTemplate(queryDslJdbcTemplate);
    }
}
