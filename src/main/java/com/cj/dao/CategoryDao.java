package com.cj.dao;


import com.cj.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryDao extends PagingAndSortingRepository<Category, Long> {
}
