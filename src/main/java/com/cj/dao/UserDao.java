package com.cj.dao;


import com.cj.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported = false)
public interface UserDao extends PagingAndSortingRepository<User, Long> {
    User findByName(String username);
}
