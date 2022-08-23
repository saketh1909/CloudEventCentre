package com.cmpe275.term.controller;

import com.cmpe275.term.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*")
public class ReviewController {

    public WebMvcConfigurer configure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }

    @Autowired
    private ReviewService service;

    @PostMapping("/createReview")
    public ResponseEntity createReview(@RequestParam Long reviewerId ,@RequestParam Long revieweeId ,@RequestParam Long eventId ,@RequestParam Integer rating ,@RequestParam(required = false) String reviewComment){
        try{
            return ResponseEntity.status(200).body(service.createReview(reviewerId,revieweeId,eventId,rating,reviewComment));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Review creation unsucessful");
        }
    }

    @GetMapping("/getReviewsForUser")
    public ResponseEntity getUserReviewsForUser(Long userId){
        try{
            return ResponseEntity.status(200).body(service.getUserReviewsForUser(userId));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/getReviewsByUser")
    public ResponseEntity getUserReviewsByUser(Long userId){
        try{
            return ResponseEntity.status(200).body(service.getUserReviewsByUser(userId));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}
