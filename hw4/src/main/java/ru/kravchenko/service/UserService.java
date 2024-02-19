package ru.kravchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kravchenko.model.User;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        userDao.createUser(user);
        System.out.println("Пользователь " + user.getUsername() + " добавлен в БД");
    }

    public User getUserById(Long userId) {
        final User userById = userDao.getUserById(userId);
        System.out.println("Пользователь " + userById.getUsername() + " получен из БД по id " + userId);
        return userById;
    }

    public List<User> getAllUsers() {
        final List<User> allUsers = userDao.getAllUsers();
        System.out.println(allUsers.size() + " пользователей в БД");
        return allUsers;
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
        System.out.println("Пользователь с id: " + user.getId() + " обновлён");
    }

    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
        System.out.println("Пользователь с id: " + userId + " удалён из БД");
    }
}
