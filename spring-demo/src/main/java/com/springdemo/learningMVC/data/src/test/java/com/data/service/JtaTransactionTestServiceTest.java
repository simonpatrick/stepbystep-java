package com.springdemo.learningMVC.data.src.test.java.com.data.service;

import com.common.base.RandomStrings;
import com.data.domain.DemoUser;
import com.data.domain.DemoUserRepository;
import com.data.domain.TUserStatus;
import com.data.domain.TestDomain;
import com.data.jms.SimpleTextMessageListener;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-context-jta.xml")
public class JtaTransactionTestServiceTest {

    @Resource
    private JtaTransactionTestService jtaTransactionTestService;

    @Resource
    private SimpleTextMessageListener simpleTextMessageListener;

    @Resource
    private DemoUserRepository demoUserRepository;

    @Test
    public void testRollback1() throws Exception {
        DemoUser user = DemoUser.newUser(null); // Null username
        TestDomain td = TestDomain.newInstance("tdName", TUserStatus.VALIDATED);

        try {
            jtaTransactionTestService.store(user, td);
        } catch (Exception ex) {
            // rollback
            assertThat(user.getId(), nullValue());
            assertThat(td.getId(), nullValue());
        }
    }

    @Test
    public void testRollback2() throws Exception {
        DemoUser user = DemoUser.newRandomUser();
        // null td name
        TestDomain td = TestDomain.newInstance(null, TUserStatus.VALIDATED);

        try {
            // 第一个库成功，第二个库失败，第一个库回滚
            jtaTransactionTestService.store(user, td);
        } catch (Exception ex) {
            System.out.println("Throwable class: " + ex.getClass());
            // rollback
            assertThat(user.getId(), notNullValue());
            // but user not found
            assertThat(jtaTransactionTestService.getUser(user.getId()),
                    nullValue());
            // but user not found
            assertThat(jtaTransactionTestService.getUser(user.getUsername()),
                    nullValue());
        }
    }

    @Test
    public void testRollback3() throws Exception {
        DemoUser user = DemoUser.newRandomUser();
        // null td name
        TestDomain td = TestDomain.newInstance(null, TUserStatus.VALIDATED);

        try {
            // 第一个库成功，第二个库失败，第一个库回滚
            jtaTransactionTestService.store2(user, td, "JMS message");
        } catch (Exception ex) {
            System.out.println("Throwable class: " + ex.getClass());
            // rollback
            assertThat(user.getId(), notNullValue());
            // but user not found
            assertThat(jtaTransactionTestService.getUser(user.getId()),
                    nullValue());
            // but user not found
            assertThat(jtaTransactionTestService.getUser(user.getUsername()),
                    nullValue());
        }

        assertTrue(simpleTextMessageListener.getMessages().isEmpty());
    }

    @Test
    public void testVersionedUser() throws Exception {
        DemoUser user = DemoUser.newRandomUser();
        demoUserRepository.save(user); // save to database, and generate userId

        // 模似另一个程序修改用户信息（版本已变）
        ThreadGroup threadGroup = new ThreadGroup("other");
        final Integer userId = user.getUserId();
        for (int i = 0; i < 1; i++) {
            Thread t = new Thread(threadGroup, () -> {
                DemoUser u = demoUserRepository.findOne(userId);
                u.setUsername(RandomStrings.randomAlphabetic(10));
                demoUserRepository.save(u);
            });
            t.start();
            t.join();
        }

        String oldPwd = user.getPassword();
        String newPwd = "12345678";
        try {
            jtaTransactionTestService.updatePassword(user, newPwd);
        } catch (Exception ex) {
            System.out.println("Exception happened! " + ex.getMessage());
            assertThat(ex, instanceOf(PessimisticLockingFailureException.class));
        }
        // not changed
        DemoUser user2 = demoUserRepository.findOne(user.getUserId());
        assertThat(oldPwd, Is.is(user2.getPassword()));
        assertThat(user.getVersion(), IsNot.not(user2.getVersion()));

        threadGroup.destroy();
    }
}
