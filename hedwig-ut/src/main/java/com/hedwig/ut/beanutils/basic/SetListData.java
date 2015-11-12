package com.hedwig.ut.beanutils.basic;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by patrick on 15/8/26.
 */
public class SetListData {
    private String id;
    private List<SampleBean> beans = Lists.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SampleBean> getBeans() {
        return beans;
    }

    public void setBeans(List<SampleBean> beans) {
        this.beans = beans;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        SetListData d = new SetListData();
        d.getBeans().add(null);
        d.getBeans().add(null);
        BeanUtils.setProperty(d, "beans[0].name", "test");
        BeanUtils.setProperty(d, "beans[1].name", "test1");
        System.out.println(d.getBeans());
    }
}
