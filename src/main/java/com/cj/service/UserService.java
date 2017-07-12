package com.cj.service;


import com.cj.model.User;


public interface UserService {
    Iterable<User> findAll();
    User findById(Long id);
    void save(User user);
    void delete(User user);
    User findByName(String username);
}
