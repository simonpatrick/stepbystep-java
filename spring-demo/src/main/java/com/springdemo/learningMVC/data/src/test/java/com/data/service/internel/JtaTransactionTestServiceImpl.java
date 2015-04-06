package com.springdemo.learningMVC.data.src.test.java.com.data.service.internel;


import com.data.domain.DemoUser;
import com.data.domain.DemoUserRepository;
import com.data.domain.TestDomain;
import com.data.domain.TestDomainRepository;
import com.data.qdomain.EntityNotFoundException;
import com.data.service.JtaTransactionTestService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service("jtaTransactionTestService")
@Transactional(readOnly = true)
public class JtaTransactionTestServiceImpl implements JtaTransactionTestService {

    private DemoUserRepository demoUserRepository;
    private TestDomainRepository testDomainRepository;
    private JmsTemplate jmsTemplate;
    private Destination defaultQueue;

    @Override
    public DemoUser getUser(Integer userId) {
        return demoUserRepository.findOne(userId);
    }

    @Override
    public DemoUser getUser(String username) {
        return demoUserRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void store(DemoUser user, TestDomain td) {
        demoUserRepository.save(user);
        testDomainRepository.save(td);
    }

    @Override
    @Transactional
    public void store2(DemoUser user, TestDomain td, String message) {
        jmsTemplate.send(defaultQueue, session ->
                session.createTextMessage(message));
        demoUserRepository.save(user);
        testDomainRepository.save(td);
    }

    @Override
    @Transactional
    public void updatePassword(DemoUser user, String newPwd) {
        if (user == null) {
            throw new EntityNotFoundException(404, "User");
        }
        user.setPassword(newPwd);
        demoUserRepository.save(user);
    }

    @Resource
    public void setDemoUserRepository(DemoUserRepository demoUserRepository) {
        this.demoUserRepository = demoUserRepository;
    }

    @Resource
    public void setTestDomainRepository(TestDomainRepository testDomainRepository) {
        this.testDomainRepository = testDomainRepository;
    }

    @Resource(name = "xaJmsTemplate")
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Resource
    public void setDefaultQueue(Destination defaultQueue) {
        this.defaultQueue = defaultQueue;
    }
}
