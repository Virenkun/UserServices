package com.userservices.controller;

import com.userservices.entities.User;
import com.userservices.payload.ApiDeleteResponse;
import com.userservices.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServicesImpl userServices;

    @Autowired
    public UserController(UserServicesImpl userServices) {
        this.userServices = userServices;
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userServices.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/createUsers")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        List<User> createdUsers = new ArrayList<>();
        for (User user : users) {
            userServices.createUser(user);
            createdUsers.add(user);
        }
        return new ResponseEntity<>(createdUsers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServices.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userServices.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDeleteResponse> deleteUser(@PathVariable String id) {
        String name = userServices.getUserById(id).getName();
        userServices.deleteUser(id);
        ApiDeleteResponse response = ApiDeleteResponse.builder()
                .message(name + " has been deleted successfully!")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
