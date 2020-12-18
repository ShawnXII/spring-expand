package com.github.spring.expand.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wx
 * @date 2020/12/15 18:50
 */
public interface TestBRepository extends JpaRepository<TestBEntity, Long>, JpaSpecificationExecutor {
}
