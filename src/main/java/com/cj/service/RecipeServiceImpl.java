package com.cj.service;

import com.cj.dao.RecipeDao;
import com.cj.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeDao recipeDao;

    @Override
    public Iterable<Recipe> findAll() {
        return recipeDao.findAll();
    }

    @Override
    public Recipe findById(Long id) {
        return recipeDao.findOne(id);
    }

    @Override
    public void save(Recipe recipe) {
        recipeDao.save(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeDao.delete(recipe);
    }

    @Override
    public List<Recipe> findByCategoryName(String category) {
        return recipeDao.findByCategoryName(category);
    }

    @Override
    public List<Recipe> findByDescriptionContains(String description) {
        return recipeDao.findByDescriptionContains(description);
    }

    @Override
    public List<Recipe> findByCategoryNameAndDescriptionContains(String category, String description) {
        return recipeDao.findByCategoryNameAndDescriptionContains(category, description);
    }
}