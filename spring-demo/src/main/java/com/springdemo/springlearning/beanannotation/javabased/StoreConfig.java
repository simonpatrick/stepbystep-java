package com.springdemo.springlearning.beanannotation.javabased;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

@Configuration
@ImportResource("classpath:spring-beanannotation.xml")
public class StoreConfig {
    private static final Logger logger = LogManager.getLogger(StoreConfig.class.getName());

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String userName;

    @Value("${password}")
    private String password;

    @Autowired
    private Store<String> ss;
    @Autowired
    private Store<Integer>si;

    public void connect(){
        logger.info("name ={}",userName);
        logger.info("url={}",url);
        logger.info("password={}",password);
    }

    @Bean
    @Scope(value = "prototype")
    public StringStore stringStore(){
        return new StringStore();
    }

    @Bean
    public IntegerStore integerStore(){
        return new IntegerStore();
    }

    @Bean
    public MyDriverManager myDriverManager(){
        return new MyDriverManager(url,userName,password);
    }
    @Bean(name="string2store")
    public String to(){
        logger.info("stringstore={}",ss);
        logger.info("stringinteger={}",si);
        return ss.toString()+si.toString();
    }
}
