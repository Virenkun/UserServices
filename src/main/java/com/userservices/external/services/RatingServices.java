package com.userservices.external.services;

import com.userservices.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE", url = "http://localhost:8083/api/v1/ratings")
public interface RatingServices {
    @GetMapping("/user/{userId}")
    List<Rating> getRatingsByUserId(@PathVariable String userId);
}
