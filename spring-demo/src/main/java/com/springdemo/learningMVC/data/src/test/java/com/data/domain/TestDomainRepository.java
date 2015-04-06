package com.springdemo.learningMVC.data.src.test.java.com.data.domain;


import com.data.repository.GenericRepository;

public interface TestDomainRepository extends GenericRepository<TestDomain, Integer> {

    public TestDomain findByName(String name);
}
