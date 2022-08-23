package com.cmpe275.term.controller;

import com.cmpe275.term.service.ReportsService;
import com.cmpe275.term.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class ReportController {
    public WebMvcConfigurer configure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }

    @Autowired
    private ReportsService service;

    @GetMapping("/getSystemReports")
    public ResponseEntity getSystemReports(@RequestParam String time){
        try{
            return ResponseEntity.status(200).body(service.generateSystemReports(time));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Report Generation Failure");
        }
    }

    @GetMapping("/getUserReports")
    public ResponseEntity getUserReports(@RequestParam String time, @RequestParam Long userId){
        try{
            return ResponseEntity.status(200).body(service.generateUserReports(time,userId));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Report Generation Failure");
        }
    }
}
