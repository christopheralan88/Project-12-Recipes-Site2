package com.cj.dao;


import com.cj.model.Ingredient;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IngredientDao extends PagingAndSortingRepository<Ingredient, Long> {
}
