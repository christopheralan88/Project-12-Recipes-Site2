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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;


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
    public void editRecipeWithLoggingIn() throws Exception {
        Recipe recipe = recipeBuilder();
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(recipeService.findById(1L)).thenReturn(recipe);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1")
                .with(userBuilder()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    public void editRecipeWithoutLoggingIn() throws Exception {
        Recipe recipe = recipeBuilder();

        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    private Recipe recipeBuilder() {
        return new Recipe("recipe1", category, "an image", null,
                null, 123L, 321L, "a yummy recipe", rightUser);
    }

    private RequestPostProcessor userBuilder() {
        return user(rightUser.getUsername())
                .roles("USER")
                .password(rightUser.getPassword());
    }

}
