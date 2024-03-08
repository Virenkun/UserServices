package com.userservices.services;

import com.userservices.entities.User;

import java.util.List;

public interface UserServices {
    User createUser(User user);
    User getUserById(String id);
    void deleteUser(String id);
    List<User> getAllUsers();
    User getUserByEmail(String email);
}
