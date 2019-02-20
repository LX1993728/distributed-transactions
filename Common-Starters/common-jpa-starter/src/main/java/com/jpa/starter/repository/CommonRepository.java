package com.jpa.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CommonRepository<T,ID> extends CrudRepository<T,ID>,JpaRepository<T,ID>, PagingAndSortingRepository<T,ID>, JpaSpecificationExecutor<T> {
}
