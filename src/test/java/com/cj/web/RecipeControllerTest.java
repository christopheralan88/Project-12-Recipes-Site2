package com.cj.web;

import com.cj.Application;
import com.cj.model.*;
import com.cj.service.CategoryService;
import com.cj.service.RecipeService;
import com.cj.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RecipeControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;
    @Mock
    private RecipeService recipeService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserService userService;
    @InjectMocks
    private RecipeController recipeController;
    private MockMvc mockMvc;
    private Category category;
    private User rightUser;
    private User wrongUser;


    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".html");

        category = new Category("Lunch");
        rightUser = new User("admin", new String[] {"ROLE_USER", "ROLE_ADMIN"}, "abc");
        wrongUser = new User("user", new String[] {"ROLE_USER"}, "def");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .addFilters(springSecurityFilterChain)
                                 //.standaloneSetup(recipeController)
                                 //.setViewResolvers(viewResolver)
                                 .build();
    }

    @After
    public void tearDown() throws Exception {
        category = null;
        rightUser = null;
        wrongUser = null;
    }

    @Test
    public void viewDetail() throws Exception {
        Recipe recipe = recipeBuilder();
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findById(1L)).thenReturn(recipe);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/detail/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("detail"));
    }

    @Test
    public void editRecipeWithRightUser() throws Exception {
        Recipe recipe = recipeBuilder();
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findById(1L)).thenReturn(recipe);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1")
                .with(rightUserBuilder()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    public void editRecipeWithoutNoUser() throws Exception {
        Recipe recipe = recipeBuilder();

        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void editRecipeWithWrongUser() throws Exception {
        Recipe recipe = recipeBuilder();

        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1")
                .with(wrongUserBuilder()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void deleteRecipeWithRightUser() throws Exception {
        Recipe recipe = recipeBuilder();

        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/delete/1")
                .with(rightUserBuilder()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void deleteRecipeWithNoUser() throws Exception {
        Recipe recipe = recipeBuilder();

        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void deleteRecipeWithWrongUser() throws Exception {
        Recipe recipe = recipeBuilder();

        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/delete/1")
                .with(wrongUserBuilder()))
                .andDo(print())
                .andExpect(flash().attributeExists("errors"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void viewAddRecipePageWithNoUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void viewAddRecipePageWithUser() throws Exception {
        Recipe recipe = recipeBuilder();
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/add")
                .with(rightUserBuilder()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    public void addRecipePageWithNoUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void toggleFavoriteWithUser() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.post("/recipe/new-favorite/1")).param("id", "1").with(rightUserBuilder()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void toggleFavoriteWithNoUser() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.post("/recipe/new-favorite/1")).param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void viewIndexWithNoSearchCriteriaAndNoUser() throws Exception {
        Recipe recipe = recipeBuilder();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findAll()).thenReturn(recipes);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(model().size(2))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void viewIndexWithNoSearchCriteriaAndUser() throws Exception {
        Recipe recipe = recipeBuilder();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findAll()).thenReturn(recipes);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/").with(rightUserBuilder()))
                .andDo(print())
                .andExpect(model().size(3))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void viewIndexWithCategorySearchCriteriaAndNoUser() throws Exception {
        String categoryCriteria = "Lunch";
        Recipe recipe = recipeBuilder();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findByCategoryName(categoryCriteria)).thenReturn(recipes);
        when(recipeService.findAll()).thenReturn(recipes);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform((MockMvcRequestBuilders.get("/"))
                .param("searchCategory", categoryCriteria))
                .andDo(print())
                .andExpect(model().size(2))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void viewIndexWithDescriptionCriteriaAndNoUser() throws Exception {
        String descriptionCriteria = "Lunch";
        Recipe recipe = recipeBuilder();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findByDescriptionContains(descriptionCriteria)).thenReturn(recipes);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform((MockMvcRequestBuilders.get("/"))
                .param("searchDescription", descriptionCriteria))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("recipes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void viewIndexWithDescriptionAndCategoryCriteriaAndNoUser() throws Exception {
        String descriptionCriteria = "3";
        String categoryCriteria = "Lunch";
        Recipe recipe = recipeBuilder();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findByCategoryNameAndDescriptionContains(categoryCriteria, descriptionCriteria)).thenReturn(recipes);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform((MockMvcRequestBuilders.get("/"))
                .param("searchDescription", descriptionCriteria)
                .param("searchCategory", categoryCriteria))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("recipes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void viewIndexWithDescriptionAndCategoryCriteriaAndUser() throws Exception {
        String descriptionCriteria = "3";
        String categoryCriteria = "Lunch";
        Recipe recipe = recipeBuilder();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findByCategoryNameAndDescriptionContains(categoryCriteria, descriptionCriteria)).thenReturn(recipes);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform((MockMvcRequestBuilders.get("/"))
                .param("searchDescription", descriptionCriteria)
                .param("searchCategory", categoryCriteria)
                .with(rightUserBuilder()))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("recipes"))
                .andExpect(model().attributeExists("user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /*@Test
    public void addRecipeWithRightUser() throws Exception {
        Recipe recipe = new Recipe("recipe1", category, "an image", null,
                null, 123L, 321L, "a yummy recipe", null);

        //don't persist recipe because category does not exist in database, so you'll get an TransientPropertyValueException.
        //RecipeService spy = Mockito.spy(recipeService);
        //doNothing().when(recipeService).save(recipe);
        when(recipeService.save(recipe)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .sessionAttr("recipe", recipe)
                .with(rightUserBuilder()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }*/

    private Recipe recipeBuilder() {
        return new Recipe("recipe1", category, "an image", null,
                null, 123L, 321L, "a yummy recipe", rightUser);
    }

    private RequestPostProcessor rightUserBuilder() {
        //returns RequestPostProcessor based on the User that was used to create
        // the recipe in recipeBuilder()
        return user(rightUser.getUsername())
                .roles("USER")
                .password(rightUser.getPassword());
    }

    private RequestPostProcessor wrongUserBuilder() {
        //returns a RequestPostProcessor that is based on a User that is different
        // from the User used to create the recipe in recipeBuilder()
        return user(wrongUser.getUsername())
                .roles("USER")
                .password(wrongUser.getPassword());
    }

}
