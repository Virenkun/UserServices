package com.userservices.services;

import com.userservices.entities.Rating;
import com.userservices.entities.User;
import com.userservices.exceptions.UserNotFoundException;
import com.userservices.external.services.RatingServices;
import com.userservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices{

    private final UserRepository userRepository;

    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private RatingServices ratingServices;
    @Autowired
    public UserServicesImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    @Override
    public User createUser(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setUserId(uuid);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
//        List<Rating> ratings = restTemplate.getForObject("http://localhost:8083/api/v1/ratings/user/"+id, List.class);
        List<Rating> ratings = ratingServices.getRatingsByUserId(id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setRatings(ratings);
        return user;
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users =  userRepository.findAll();
        for (User user : users) {
            List<Rating> ratings = restTemplate.getForObject("http://localhost:8083/api/v1/ratings/user/"+user.getUserId(), List.class);
            user.setRatings(ratings);
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
