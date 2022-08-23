package com.cmpe275.term;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RunWith(SpringRunner.class)
public class getEventDetails {

    @Test
    public void eventTest1() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/event/getEvent?eventId=714";
        URI uri = new URI(baseUrl);
        ResponseEntity<?> response = restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void eventTest2() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/event/getEvent?eventId=814";
        URI uri = new URI(baseUrl);
        ResponseEntity<?> response = restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(200, response.getStatusCodeValue());
    }
}
