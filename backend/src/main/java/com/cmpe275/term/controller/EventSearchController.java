package com.cmpe275.term.controller;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.model.EventResponse;
import com.cmpe275.term.service.EventSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "*")
public class EventSearchController {

    @Autowired
    private EventSearchService service;

    public WebMvcConfigurer configure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }

    @GetMapping("/searchEvent")
    public ResponseEntity eventSearch(@RequestParam String location , @RequestParam String status, @RequestParam String startTime,
                                      @RequestParam(required = false) String endTime,@RequestParam(required = false) String keyword,@RequestParam(required = false) String organizer){
        try{
            List<EventResponse> events = service.eventSearch(location,status,startTime,endTime,keyword,organizer);
            return ResponseEntity.status(200).body(events);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/eventSignUp")
    public ResponseEntity eventSignUp(@RequestParam Long userId , @RequestParam Long eventId , @RequestParam Integer approval){
        try{
            return ResponseEntity.status(200).body(service.eventSignUp(userId,eventId,approval));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Event Sign Up Unsuccessful");
        }
    }

    @PutMapping("/eventRequestApproval")
    public ResponseEntity eventApprovalRequest(@RequestParam Long participantId , @RequestParam Integer approval){
        try{
            return ResponseEntity.status(200).body(service.eventSignUpApprovalRequest(participantId,approval));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Event Approval Request Processing Unsuccessful");
        }
    }

    @GetMapping("/getEventApprovalRequests")
    public ResponseEntity getEventApprovalRequest(@RequestParam Long eventId){
        try{
            return ResponseEntity.status(200).body(service.getEventApprovalRequest(eventId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to retrieve Event Approval Requests");
        }
    }

}
