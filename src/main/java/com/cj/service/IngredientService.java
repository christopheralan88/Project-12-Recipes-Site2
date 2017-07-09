package com.cj.service;

import com.cj.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IngredientService {
    Iterable<Ingredient> findAll();
    Ingredient findById(Long id);
    void save(Ingredient ingredient);
    void delete(Ingredient ingredient);
}