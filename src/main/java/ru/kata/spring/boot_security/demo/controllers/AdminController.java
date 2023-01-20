package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "admin/admin";
    }


    //new
    @PostMapping( "/edit/{id}")
    public String updateUser(ModelMap model, @PathVariable Long id, @ModelAttribute("user") User user) {
        adminService.update(user);
        return "redirect:/admin";
    }


    //Удаление пользователя
    @GetMapping("/delete")
    public String getListToDelete(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/delete";
    }

    @GetMapping("/{id}/deleteById")
    public String getUserToDelete(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "admin/show";
    }
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return "redirect:/admin";
    }
    //Вернуть пользователя по id
    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "admin/show";
    }
    //Создать пользователя
    @GetMapping("/new")
    public String newPage(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    //Обновить пользователя
    @GetMapping("/update")
    public String getListToUpdate(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/admin/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        System.out.println("patch - 1");
        adminService.update(user);
        System.out.println("patch - 2");
        return "redirect:/admin";
    }






}
