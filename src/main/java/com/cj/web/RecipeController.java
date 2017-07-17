package com.cj.web;

import com.cj.model.*;
import com.cj.service.CategoryService;
import com.cj.service.RecipeService;
import com.cj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;


@Controller
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;


    public RecipeController() {}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewAllRecipes(@RequestParam(value = "searchCategory", required = false, defaultValue = "") String category,
                                 @RequestParam(value = "searchDescription", required = false, defaultValue = "") String description,
                                 Principal principal,
                                 ModelMap model) {
        List<Recipe> recipes;
        if (! category.equals("") && description.equals("")) {
            recipes = recipeService.findByCategoryName(category);
        } else if (category.equals("") && ! description.equals("")) {
            recipes = recipeService.findByDescriptionContains(description);
        } else if (! category.equals("") && ! description.equals("")) {
            recipes = recipeService.findByCategoryNameAndDescriptionContains(category, description);
        } else {
            recipes = (List<Recipe>)recipeService.findAll();
        }
        recipes.sort(Comparator.comparing(Recipe::getName));

        if (principal != null) {
            User user = userService.findByName(principal.getName());
            model.put("user", user);

            if (user.getFavorites().size() > 0 && recipes.size() > 0) {
                for (Recipe recipe : recipes) {
                    if (user.getFavorites().contains(recipe)) {
                        recipe.setFavorited(true);
                    }
                }
            }
        }

        model.put("recipes", recipes);
        model.put("categories", categoryService.findAll());

        return "index";
    }

    @RequestMapping(value = "/recipe/new-favorite/{id}", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            User user = userService.findByName(principal.getName());
            Recipe recipe = recipeService.findById(id);
                if (user.getFavorites().contains(recipe)) {
                    user.getFavorites().remove(recipe);
                } else {
                    user.getFavorites().add(recipe);
                }
            userService.save(user);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String viewAddRecipe(ModelMap model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        List<Category> categories = (List<Category>)categoryService.findAll();
        model.put("categories", categories);

        Recipe recipe = new Recipe();
        //the recipe can't have null for category field in order for edit view to work.
        recipe.setCategory(categories.get(0));
        model.put("recipe", recipe);

        model.put("newIngredient", new Ingredient());
        model.put("newInstruction", new Instruction());

        return "edit";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addRecipe(@Valid Recipe recipe, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipe", recipe);
            redirectAttributes.addFlashAttribute("errors", "Please fill out the form completely");
            return "redirect:/errors";
        }

        recipeService.save(recipe);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editRecipe(@PathVariable Long id, ModelMap model, Principal principal, RedirectAttributes redirectAttributes) {
        Recipe recipe = recipeService.findById(id);
        if (principal == null) {
            return "redirect:/login";
        }
        if (principal.getName().equals(recipe.getUser().getUsername())) {
            model.put("categories", categoryService.findAll());
            model.put("recipe", recipeService.findById(id));
            return "edit";
        } else {
            redirectAttributes.addFlashAttribute("errors", "You can only edit recipes that you own");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteRecipe(@PathVariable Long id, ModelMap model) {
            recipeService.delete(recipeService.findById(id));
        return "redirect:/";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String vewRecipeDetail(@PathVariable Long id, ModelMap model) {
        model.put("recipe", recipeService.findById(id));
        model.put("categories", categoryService.findAll());
        return "detail";
    }

    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public String viewErrors() {
        return "errors";
    }
}