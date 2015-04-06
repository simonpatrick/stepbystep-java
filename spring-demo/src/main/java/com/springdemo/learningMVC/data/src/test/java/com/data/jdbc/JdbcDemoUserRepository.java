package com.springdemo.learningMVC.data.src.test.java.com.data.jdbc;

import com.data.domain.DemoUser;
import com.data.domain.DemoUserRepository;
import com.data.qdomain.jdbc.QueryDslJdbcTemplate;
import com.data.repository.listener.DefaultEntityUpdateListener;
import com.data.repository.support.QueryDslJdbcRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.data.domain.QDemoUser.qdu;

@Repository("demoUserRepository")
public class JdbcDemoUserRepository extends QueryDslJdbcRepository<DemoUser, Integer>
        implements DemoUserRepository, InitializingBean {

    @Resource(name = "defaultEntityUpdateListener")
    private DefaultEntityUpdateListener updateListener;

    public JdbcDemoUserRepository() {
        super(qdu);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        addUpdateListener(updateListener);
    }

    @Override
    public DemoUser findByUsername(String username) {
        return findOne(qdu.username.eq(username));
    }

    @Override
    @Autowired(required = false)
    @Qualifier("ds1QueryDslJdbcTemplate")
    public void setQueryDslJdbcTemplate(QueryDslJdbcTemplate queryDslJdbcTemplate) {
        super.setQueryDslJdbcTemplate(queryDslJdbcTemplate);
    }
}
