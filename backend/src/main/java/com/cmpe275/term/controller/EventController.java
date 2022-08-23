package com.cmpe275.term.controller;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Participant;
import com.cmpe275.term.model.EventResponse;
import com.cmpe275.term.model.ParticipantResponse;
import com.cmpe275.term.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService service;

    public WebMvcConfigurer configure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }
    @PostMapping("/createEvent")
    public ResponseEntity createEvent (@RequestParam Long userId, @RequestParam String title, @RequestParam(required = false) String description,
                                      @RequestParam String startTime, @RequestParam String endTime, @RequestParam String deadline,
                                      @RequestParam(required = false) String street,
                                      @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) String zip,
                                      @RequestParam Integer minParticipants, @RequestParam Integer maxParticipants, @RequestParam Double fees, @RequestParam String policy){
        try{
            java.sql.Timestamp startTimeTimestamp = java.sql.Timestamp.valueOf(startTime);
            java.sql.Timestamp endTimeTimestamp = java.sql.Timestamp.valueOf(endTime);
            java.sql.Timestamp deadlineTimestamp = java.sql.Timestamp.valueOf(deadline);
            EventResponse e = this.service.createEvent(userId,title,description,street,city,state,zip,startTimeTimestamp,endTimeTimestamp,deadlineTimestamp,minParticipants,maxParticipants,fees,policy);
            return ResponseEntity.of(Optional.of(e));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/getEvent")
    public ResponseEntity getEvent(@RequestParam Long eventId){
        try{
            EventResponse e =this.service.getEvent(eventId);
            return ResponseEntity.of(Optional.of(e));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/getEventsCreatedByUser")
    public ResponseEntity getEventsCreatedByUser(@RequestParam Long userId){
        try{
            List<EventResponse> list=service.getEventsCreatedByUser(userId);
            return ResponseEntity.of(Optional.of(list));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/getEventsRegisteredByUser")
    public ResponseEntity getEventsRegisteredByUser(@RequestParam Long userId){
        try{
            List<ParticipantResponse> list=service.getEventsRegisteredByUser(userId);
            return ResponseEntity.of(Optional.of(list));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/cancelEvent")
    public ResponseEntity cancelEvent (@RequestParam Long eventID){
        try{
            return ResponseEntity.status(200).body(this.service.cancelEvent(eventID));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Event Cancellation Unsuccessful");
        }
    }
}
