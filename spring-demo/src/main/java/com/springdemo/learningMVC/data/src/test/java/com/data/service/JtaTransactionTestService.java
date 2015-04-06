
package com.springdemo.learningMVC.data.src.test.java.com.data.service;


import com.data.domain.DemoUser;
import com.data.domain.TestDomain;

public interface JtaTransactionTestService {

    public DemoUser getUser(Integer userId);

    public DemoUser getUser(String username);

    /**
     * 任何一步错误则回滚
     */
    public void store(DemoUser user, TestDomain td);

    /**
     * 保存 user, td 后，发送 jms 消息错误时，前两个也回滚。
     */
    public void store2(DemoUser user, TestDomain td, String message);

    public void updatePassword(DemoUser user, String newPwd);
}
