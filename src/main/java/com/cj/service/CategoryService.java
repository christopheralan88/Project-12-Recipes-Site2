package com.cj.service;


import com.cj.model.Category;

import java.util.List;

public interface CategoryService {
    Iterable<Category> findAll();
    Category findById(Long id);
    void save(Category category);
    void delete(Category category);
}