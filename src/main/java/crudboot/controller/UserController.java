package crudboot.controller;

import crudboot.model.User;

import crudboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirectUser(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userService.getUserByName(authentication.getName()).isAdmin()) {
            return "redirect: /admin";
        } else {
            return "redirect: /user";
        }
    }

    @GetMapping("/login")
    public String login(ModelMap model) {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }

        model.addAttribute("errorMessage", errorMessage);

        return "login";
    }

    @GetMapping("/user")
    public String printUserInfo(ModelMap model) {
        model.addAttribute("pageTitle", "User information-page");
        model.addAttribute("tableTitle", "About user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", userService.getUserByName(authentication.getName()));

        return "/user";
    }

    @GetMapping("/admin")
    public String printUsersList(ModelMap model) {
        model.addAttribute("pageTitle", "Admin information-page");
        model.addAttribute("tableTitle", "Admin user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", userService.getUserByName(authentication.getName()));
        model.addAttribute("usersList", userService.getUsersList());

        model.addAttribute("newUser", new User());
        model.addAttribute("rolesList", userService.getAvailableRoles());

        return "/admin/index";
    }

    @RequestMapping(value = "/admin/userdelete", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute("user") User user, ModelMap model) {
        userService.delete(user);
        return "redirect:" + "/admin";
    }

    @RequestMapping(value = "/admin/usersave", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute("user") User newUser, ModelMap model) {
        userService.add(newUser);
        return "redirect:" + "/admin";
    }

    @RequestMapping(value = "/admin/userupdate", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, ModelMap model) {
        userService.update(user);
        return "redirect:" + "/admin";
    }

}
