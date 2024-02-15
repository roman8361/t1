package ru.kravchenko;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kravchenko.config.AppConfig;
import ru.kravchenko.model.User;
import ru.kravchenko.service.UserService;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        final UserService userService = context.getBean(UserService.class);
        final User user = new User(4L, "Алиса Ольховская");

        userService.createUser(user);
        userService.deleteUser(user.getId());
        userService.getUserById(3L);
        userService.getAllUsers();
        userService.updateUser(new User(3L, "Лена Мент"));
    }
}