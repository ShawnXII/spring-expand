package com.github.spring.expand.data.repository;

import com.github.spring.expand.base.Entity;
import com.github.spring.expand.data.search.Searchable;
import com.github.spring.expand.data.search.build.BuildFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author wx
 * @date 2020/12/15 18:58
 */
@NoRepositoryBean
public interface BaseRepository<T extends Entity<ID>, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor {
    /**
     * 根据条件查询
     *
     * @param searchable
     * @return
     */
    default List<T> findAll(Searchable searchable) {
        return this.findAll(searchable.build().toSpecification());
    }

    /**
     * 根据条件查询一条记录
     *
     * @param searchable
     * @return
     */
    default Optional<T> findOne(Searchable searchable) {
        return this.findOne(searchable.build().toSpecification());
    }

    /**
     * 分页查询
     *
     * @param searchable
     * @return
     */
    default Page<T> findPage(Searchable searchable) {
        BuildFactory build = searchable.build();
        return this.findAll(build.toSpecification(), build.toPageable());
    }


}
