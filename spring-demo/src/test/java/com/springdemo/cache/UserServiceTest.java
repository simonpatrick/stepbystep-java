package com.springdemo.cache;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest {
    @Autowired
    private UserService2 userService;

    @Autowired
    private CacheManager cacheManager;

    private Cache userCache;
    private Cache userCache2;
    private Cache userCache3;

    @Before
    public void setUp() {
        userCache = cacheManager.getCache("user");
        userCache2 = cacheManager.getCache("user2");
        userCache3 = cacheManager.getCache("user3");
    }


    @Test
    public void testCache() {
        Long id = 1L;
        User user = new User("patrick",id,"simon");
        userService.save(user);
        Assert.assertNotNull(userCache.get(id).get());
        Assert.assertNotNull(userCache2.get(id).get());
        Assert.assertNotNull(userCache3.get(id).get());
        System.out.println(userService.findById(id));
        user.setUserName("test update");
        user.setId(2L);
        userService.update(user);

        System.out.println(userCache.get(id).get());
        System.out.println(userCache2.get(id).get());
        System.out.println(userCache3.get(id).get());

        // this is interesting point, the both 1 and 2 key mapping to same value
        System.out.println(userCache.get(2L).get());
        System.out.println(userCache2.get(2L).get());
        System.out.println(userCache3.get(2L).get());

    }
}