package ru.kata.spring.boot_security.demo.setters;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.AdminService;
@Component
public class AdminRoleSetter implements ApplicationRunner {
    private AdminService adminService;

    public AdminRoleSetter(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User("admin", "admin", "John", "Wick", 45, "wick@mail.ru");
        adminService.saveAdmin(user);
    }
}
