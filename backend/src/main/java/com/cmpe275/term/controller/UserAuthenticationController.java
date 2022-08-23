package com.cmpe275.term.controller;

import com.cmpe275.term.entity.User;
import com.cmpe275.term.model.UserSpecialResponse;
import com.cmpe275.term.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping("/userAuth")
@CrossOrigin(origins = "*")
public class UserAuthenticationController {

    @Autowired
    private UserAuthenticationService service;

    public WebMvcConfigurer configure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }

    @PostMapping("/userSignUp")
    public ResponseEntity userSignUp(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                                           @RequestParam(required = false) String description, @RequestParam(required = false) String street,
                                           @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) String zip,
                                           @RequestParam(required = false) String accountType, @RequestParam(required = false) String screenName, @RequestParam(required = false) String gender){
        try{
            User u =this.service.userSignUp(name, email,password, description, street, city, state, zip, accountType, screenName, gender);
            return ResponseEntity.of(Optional.of(u));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/userSignIn")
    public ResponseEntity userSignIn(@RequestParam String email,@RequestParam String password,@RequestParam String authType) {
        try{
            UserSpecialResponse u = this.service.userSignIn(email,password,authType);
            return ResponseEntity.of(Optional.of(u));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @GetMapping("/validateUser")
    public ResponseEntity validateUser(@RequestParam String email , @RequestParam String otp){
        try{
            String response = this.service.validateUserWithOTP(email,otp);
            return ResponseEntity.of(Optional.of(response));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
