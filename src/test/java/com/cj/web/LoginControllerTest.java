package com.cj.web;

import com.cj.Application;
import com.cj.model.Category;
import com.cj.model.User;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class LoginControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;
    @Mock
    private UserService userService;
    @InjectMocks
    private LoginController loginController;
    private MockMvc mockMvc;
    private User rightUser;
    private User wrongUser;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".html");

        rightUser = new User("admin", new String[] {"ROLE_USER", "ROLE_ADMIN"}, "abc");
        wrongUser = new User("user", new String[] {"ROLE_USER"}, "def");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        rightUser = null;
        wrongUser = null;
    }

    @Test
    public void viewLoginFormWhenRedirectedWithFlashMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .sessionAttr("flash", "You are not logged in!"))
                .andDo(print())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("flash"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    private RequestPostProcessor rightUserBuilder() {
        //returns RequestPostProcessor based on the User that was used to create
        // the recipe in recipeBuilder()
        return user(rightUser.getUsername())
                .roles("USER")
                .password(rightUser.getPassword());
    }


}
