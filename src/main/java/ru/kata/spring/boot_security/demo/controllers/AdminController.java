package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.AdminService;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private AdminService adminService;
    private RoleService roleService;
    @Autowired
    public AdminController(UserService userService,
                           AdminService adminService,
                           RoleService roleService) {
        this.userService = userService;
        this.adminService = adminService;
        this.roleService = roleService;
    }

    //Список пользователей
    @GetMapping
    public String adminPage(Model model, Principal principal, @ModelAttribute("new_user") User new_user) {
        User user = userService.findByUsername(principal.getName());
        List<User> allUsers = userService.findAll();
        List<Role> allRoles = roleService.findAll();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("user", user);
        model.addAttribute("new_user", new_user);
        return "admin/admin";
    }


    //new
    @PostMapping( "/edit/{id}")
    public String updateUser(ModelMap model, @PathVariable int id, @ModelAttribute("user") User user,
                             @ModelAttribute ("role") Role role) {
        adminService.update(user);
        return "redirect:/admin";

    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("new_user") User new_user) {
        userService.save(new_user);
        return "redirect:/admin";
    }

    @PostMapping(value = "deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        adminService.delete(id);
        return "redirect:/admin";
    }






}
