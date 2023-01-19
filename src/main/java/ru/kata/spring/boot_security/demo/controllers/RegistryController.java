package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
@Controller
@RequestMapping("/")
public class RegistryController {
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    private UserService userService;
    @GetMapping
    public String registrationPage() {
        return "index";
    }
    @GetMapping("/new")
    public String newPage(@ModelAttribute("user") User user) {
        return "registry/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        if (userService.save(user)) {
            return "user/user";
        } else return "index";
    }
}
