package com.cj.service;

import com.cj.dao.IngredientDao;
import com.cj.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    private IngredientDao ingredientDao;

    @Override
    public Iterable<Ingredient> findAll() {
        return ingredientDao.findAll();
    }

    @Override
    public Ingredient findById(Long id) {
        return ingredientDao.findOne(id);
    }

    @Override
    public void save(Ingredient ingredient) {
        ingredientDao.save(ingredient);
    }

    @Override
    public void delete(Ingredient ingredient) {
        ingredientDao.delete(ingredient);
    }
}