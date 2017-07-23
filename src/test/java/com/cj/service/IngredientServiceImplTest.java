package com.cj.service;

import com.cj.dao.CategoryDao;
import com.cj.dao.IngredientDao;
import com.cj.model.Category;
import com.cj.model.Ingredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceImplTest {
    @Mock
    private IngredientDao ingredientDao;
    @InjectMocks
    private IngredientService ingredientService = new IngredientServiceImpl();
    private Ingredient ingredient1= new Ingredient("ingredient1", "1 cup", "good");
    private Ingredient ingredient2 = new Ingredient("ingredient2", "2 cups", "great");

    @Test
    public void findAll_returnsList() {
        Iterable ingredients = Arrays.asList(ingredient1, ingredient2);

        when(ingredientDao.findAll()).thenReturn(ingredients);

        assertTrue(ingredientService.findAll().equals(ingredients));
    }

    @Test
    public void findById_returnsOne() {
        when(ingredientDao.findOne(1L)).thenReturn(ingredient1);

        assertTrue(ingredientService.findById(1L).equals(ingredient1));
    }

    @Test
    public void save_worksCorrectly() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);

        //categories list acts as in-memory persistence.
        //when save method is called, add Category to in-memory persistence instead of database.
        when(ingredientDao.save(any(Ingredient.class)))
                .thenAnswer(a -> ingredients.add(ingredient2));
        ingredientService.save(ingredient2);

        assertTrue(ingredients.size() == 2);
    }

    @Test
    public void delete_worksCorrectly() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);

        //categories list acts as in-memory persistence.
        //when delete method is called, remove Category to in-memory persistence instead of database.
        doAnswer(a -> ingredients.remove(ingredient1))
                .when(ingredientDao)
                .delete(ingredient1);
        ingredientService.delete(ingredient1);

        assertTrue(ingredients.size() == 0);
    }
}
