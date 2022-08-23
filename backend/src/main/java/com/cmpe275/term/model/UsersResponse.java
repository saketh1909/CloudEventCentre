package com.cmpe275.term.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {
    private long id;
    private String email;
    private String accountType;
    private String name;
    private String screenName;
    private String gender;
    private String description;
    private Address address;
}
