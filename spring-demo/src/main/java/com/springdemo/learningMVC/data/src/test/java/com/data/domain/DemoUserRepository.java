package com.springdemo.learningMVC.data.src.test.java.com.data.domain;


import com.data.repository.GenericRepository;

/**
 * @author fuchun
 * @version $Id: DemoUserRepository.java 447 2014-11-11 06:47:34Z fuchun $
 * @since 2.0
 */
public interface DemoUserRepository extends GenericRepository<DemoUser, Integer> {

    public DemoUser findByUsername(String username);
}
