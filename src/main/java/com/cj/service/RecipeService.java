package com.cj.service;


import com.cj.model.Recipe;

import java.util.List;

public interface RecipeService {
    Iterable<Recipe> findAll();
    Recipe findById(Long id);
    void save(Recipe recipe);
    void delete(Recipe recipe);
    List<Recipe> findByCategoryName(String category);
    List<Recipe> findByDescriptionContains(String description);
    List<Recipe> findByCategoryNameAndDescriptionContains(String category, String description);
}