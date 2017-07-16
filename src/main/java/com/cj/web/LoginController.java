package com.cj.web;

import com.cj.dao.UserDao;
import com.cj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;


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
    public String loginVerification(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes,
                                    Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", "Incorrect username and/or password");
            return "redirect:/login";
        }

        User userToLogin = userDao.findByUsername(user.getUsername());
        if (userToLogin != null) {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/sign-up")
    public String viewSignUp() {
        return "signup";
    }
}
