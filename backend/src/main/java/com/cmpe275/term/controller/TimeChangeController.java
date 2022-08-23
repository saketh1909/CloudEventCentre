package com.cmpe275.term.controller;

import com.cmpe275.term.service.TimeChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@RequestMapping("/time")
@CrossOrigin(origins = "*")
public class TimeChangeController {

    public WebMvcConfigurer configure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }

    @Autowired
    private TimeChangeService service;

    @PutMapping("/changeCurrentTime")
    public ResponseEntity makeModificationToDatabase(@RequestParam String time){
        try{
            return ResponseEntity.status(200).body(service.makeModificationToDatabase(time));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Time Change is unsuccessful");
        }
    }


}
