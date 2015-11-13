package com.hedwig.stepbystep.javatutorial.beanutils;

import com.hedwig.stepbystep.javatutorial.beanutils.model.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BeanHelperTest {

    private User user = new User();

    @BeforeClass
    public void init(){
        user.setAge(12);
        user.setName("test");
        user.setNickName("testernick");
    }

    @Test
    public void testGetSimpleValue() throws Exception {
        Assert.assertEquals(BeanHelper.getSimpleValue(user,"name"),"test");
        Assert.assertEquals(BeanHelper.getSimpleValue(user,"nickName"),"testernick");
    }
}