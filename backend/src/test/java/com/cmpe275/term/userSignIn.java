package com.cmpe275.term;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RunWith(SpringRunner.class)
public class userSignIn {

    @Test
    public void userSignInTest1() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/userAuth/userSignIn?email=knack1239@gmail.com&password=sak123&authType";
        URI uri = new URI(baseUrl);
        ResponseEntity<?> response = restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void userSignInTest2() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/userAuth/userSignIn?email=sakethgali@gmail.com&password=sak123&authType";
        URI uri = new URI(baseUrl);
        ResponseEntity<?> response = restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(200, response.getStatusCodeValue());
    }
}
