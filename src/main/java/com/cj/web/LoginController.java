package com.cj.web;

import com.cj.model.User;
import com.cj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);

            request.getSession().removeAttribute("flash");
        } catch (Exception ex) {
            // "flash" session attribute must not exist...do nothing and proceed normally
        }
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginVerification(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttributes,
                                    Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", "Incorrect username and/or password");
            return "redirect:/login";
        }

        User userToLogin = userService.findByUsername(user.getUsername());
        if (userToLogin != null) {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String viewSignUp(ModelMap model) {
        model.put("user", new User());
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public String addUser(@RequestParam(value = "password") String password,
                          @Valid User user, BindingResult result,
                          RedirectAttributes redirectAttributes) {
        User user1 = userService.findByUsername(user.getUsername());
        /*List<User> users = (List)userService.findAll();
        boolean usernameAlreadyExists = users.stream()
                                            .findFirst().filter(u -> u.getUsername().equals(user.getUsername()))
                                                .isPresent();*/
        if (user1 != null) {
            redirectAttributes.addFlashAttribute("error", "Username already exists");
            return "redirect:/signup";
        }
        String[] userRoles = new String[] {"ROLE_USER"};
        user.setRoles(userRoles);
        userService.save(user);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException ex) {

        }
        return "redirect:/login";
    }
}
