package com.springdemo.springlearning.beanannotation.multibean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("invoker")
public class BeanInvoker {
    private static final Logger logger = LogManager.getLogger(BeanInvoker.class.getName());
    @Autowired
    private List<Bean> beans;
    @Autowired
    private Map<String,Bean> beanMap;

    @Autowired
    @Qualifier("bean2Impl")
    public Bean bean2;

    public void say() {
        for (Bean bean : beans) {
            logger.info("the bean name:{}", bean.getClass().getCanonicalName());
            bean.name();
        }
        for (Map.Entry<String, Bean> entry : beanMap.entrySet()) {
            logger.info("key={},value={}", entry.getKey(), entry.getValue());
            entry.getValue().name();
        }
    }
}
