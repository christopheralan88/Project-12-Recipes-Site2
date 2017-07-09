package com.cj.dao;


import com.cj.model.Recipe;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecipeDao extends PagingAndSortingRepository<Recipe, Long> {
    List<Recipe> findByCategoryName(String category);
    List<Recipe> findByDescriptionContains(String description);
    List<Recipe> findByCategoryNameAndDescriptionContains(String category, String description);

}
