package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.AdminService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private AdminService adminService;
    @Autowired
    public AdminController(UserService userService,
                           AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    //Список полбзователей
    @GetMapping
    public String adminPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());;
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAll());
        return "admin/admin";
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

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", adminService.findOne(id));
        return "/admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        System.out.println("patch - 1");
        adminService.update(user);
        System.out.println("patch - 2");
        return "redirect:/admin";
    }



}
