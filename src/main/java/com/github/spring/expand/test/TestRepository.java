package com.github.spring.expand.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wx
 * @date 2020/12/15 15:28
 */
public interface TestRepository extends JpaRepository<TestEntity, Long>, JpaSpecificationExecutor {

}
