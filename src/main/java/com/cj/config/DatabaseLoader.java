package com.cj.config;

import com.cj.dao.*;
import com.cj.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;
    private InstructionDao instructionDao;
    private CategoryDao categoryDao;
    //private UserDao userDao;

    @Autowired
    public DatabaseLoader(RecipeDao recipeDao, IngredientDao ingredientDao, InstructionDao instructionDao, CategoryDao categoryDao) {
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.instructionDao = instructionDao;
        this.categoryDao = categoryDao;
        //this.userDao = userDao;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*List<User> userList = Arrays.asList(
                new User("admin", new String[] {"ROLE_USER", "ROLE_ADMIN"}, "abc"),
                new User("user", new String[] {"ROLE_USER"}, "def"),
                new User("admin2", new String[] {"ROLE_USER", "ROLE_ADMIN"}, "ghi")
        );
        userDao.save(userList);*/

        //save all categories
        Category categoryAll = new Category("All");
        categoryDao.save(categoryAll);

        Category categoryBreakfast = new Category("Breakfast");
        categoryDao.save(categoryBreakfast);

        Category categoryLunch = new Category("Lunch");
        categoryDao.save(categoryLunch);

        Category categoryDinner = new Category("Dinner");
        categoryDao.save(categoryDinner);

        List<Ingredient> ingredients = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();

        IntStream.range(1, 10)
                .forEach(i -> {
                    //remove item added to lists from previous loop
                    if (ingredients.size() > 0 && instructions.size() > 0) {
                        ingredients.remove(0);
                        instructions.remove(0);
                    }

                    //create new recipe without instructions, ingredients, or categories then save recipe
                    Recipe recipe = new Recipe("recipe" + i, categoryLunch, "an image string",
                            null, null, 1L, 1L, "A description for a really yummy recipe" + i);
                    recipeDao.save(recipe);

                    //create new ingredient, reference recipe, and save ingredient
                    Ingredient ingredient = new Ingredient("ingredient" + i, "2 cups", "Fresh");
                    ingredientDao.save(ingredient);

                    //create new instruction, reference recipe, and save instruction
                    Instruction instruction = new Instruction( 1L, "An instruction" + i);
                    instructionDao.save(instruction);

                    //add ingredient and instruction to lists and add lists to recipe, then save recipe (this updates the recipe since it was already saved)
                    //categoryDao.save(categoryLunch); //update now that recipe has been saved with categoryLunch
                    ingredients.add(ingredient);
                    recipe.setIngredients(ingredients);

                    instructions.add(instruction);
                    recipe.setInstructions(instructions);

                    //recipe.setCategory(categoryLunch);

                    recipeDao.save(recipe);
                });
    }
}
