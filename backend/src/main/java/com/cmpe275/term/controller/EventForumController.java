package com.cmpe275.term.controller;


import com.cmpe275.term.model.EventForumResponse;
import com.cmpe275.term.service.EventForumService;
import com.cmpe275.term.service.EventSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eventForum")
@CrossOrigin(origins = "*")
public class EventForumController {


    @Autowired
    private EventForumService service;

    public WebMvcConfigurer configure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }
    @PostMapping("/createForumQuestion")
    public ResponseEntity createForumEventQuestion(@RequestParam  Long eventId,@RequestParam Long userId,@RequestParam String question,@RequestParam Integer forumType){
        try{
            return ResponseEntity.status(200).body(this.service.createForumEventQuestion(eventId,userId,question,forumType));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Event Forum Question Creation Unsuccessful");
        }
    }

    @PostMapping("/createForumAnswer")
    public ResponseEntity createEventForumAnswer(Long questionId,Long userId,String answer,String imageURL){
        try{
            return ResponseEntity.status(200).body(this.service.createForumEventAnswer(questionId,userId,answer,imageURL));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Event Forum Answer Creation Unsuccessful");
        }
    }

    @GetMapping("/getForumQuestionAndAnswer")
    public List<EventForumResponse> getForumQuestionsAndAnswers(Long eventId, Integer forumType){
        try{
            return this.service.getEventForums(eventId,forumType);
        }
        catch(Exception e){
            return new ArrayList<>();
        }
    }

    @PutMapping("/closeParticipantForum")
    public ResponseEntity<String> closeParticipantForum(Long eventId){
        try{
            return ResponseEntity.status(500).body(this.service.closeParticipantForum(eventId));
        }
        catch(Exception e){
            return ResponseEntity.status(500).body("Closing the forum Failed");
        }
    }
}
