package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Professor {

    private int id;
    private String firstName;
    private String lastName;
    private String email;


    private int dept_id;
}
