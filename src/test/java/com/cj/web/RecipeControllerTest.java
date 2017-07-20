package com.cj.web;

import com.cj.Application;
import com.cj.model.Category;
import com.cj.model.Recipe;
import com.cj.model.User;
import com.cj.service.CategoryService;
import com.cj.service.RecipeService;
import com.cj.service.UserService;
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

import static org.mockito.Mockito.doNothing;
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
    private Category category = new Category("Lunch");
    private User rightUser = new User("admin", new String[] {"ROLE_USER", "ROLE_ADMIN"}, "abc");
    private User wrongUser = new User("user", new String[] {"ROLE_USER"}, "def");


    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".html");

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .addFilters(springSecurityFilterChain)
                                 //.standaloneSetup(recipeController)
                                 //.setViewResolvers(viewResolver)
                                 .build();
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
    public void addRecipeWithNoUser() throws Exception {
        Recipe recipe = recipeBuilder();

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .sessionAttr("recipe", recipe))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
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
