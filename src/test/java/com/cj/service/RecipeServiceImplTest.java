package com.cj.service;

import com.cj.dao.IngredientDao;
import com.cj.dao.RecipeDao;
import com.cj.model.Ingredient;
import com.cj.model.Recipe;
import com.cj.model.User;
import com.cj.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {
    @Mock
    private RecipeDao recipeDao;
    @Mock
    private UserService userService;
    @InjectMocks
    private RecipeService recipeService = new RecipeServiceImpl();
    private User user = new User("admin", new String[] {"ROLE_USER", "ROLE_ADMIN"}, "abc");
    private Recipe recipe1 = new Recipe("recipe1", null, "an image", null,
            null, 123L, 321L, "a yummy recipe", user);
    private Recipe recipe2 = new Recipe("recipe2", null, "an image", null,
            null, 123L, 321L, "a yummy recipe", user);

    @Test
    public void findAll_returnsList() {
        Iterable recipes = Arrays.asList(recipe1, recipe2);

        when(recipeDao.findAll()).thenReturn(recipes);

        assertTrue(recipeService.findAll().equals(recipes));
    }

    @Test
    public void findById_returnsOne() {
        when(recipeDao.findOne(1L)).thenReturn(recipe1);

        assertTrue(recipeService.findById(1L).equals(recipe1));
    }

    @Test
    public void save_worksCorrectly() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);

        //categories list acts as in-memory persistence.
        //when save method is called, add Category to in-memory persistence instead of database.
        when(recipeDao.save(any(Recipe.class)))
                .thenAnswer(a -> recipes.add(recipe2));
        recipeService.save(recipe2);

        assertTrue(recipes.size() == 2);
    }

    @Test
    public void delete_worksCorrectly() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);

        //categories list acts as in-memory persistence.
        //when delete method is called, remove Category to in-memory persistence instead of database.
        when(userService.findAll()).thenReturn(Collections.singletonList(user));
        doNothing().when(userService).save(user);
        doAnswer(a -> recipes.remove(recipe1))
                .when(recipeDao)
                .delete(recipe1);
        recipeService.delete(recipe1);

        assertTrue(recipes.size() == 0);
    }
}
