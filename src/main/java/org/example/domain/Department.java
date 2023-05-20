package org.example.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Department {

    private int id;
    private String name;
    private String school;

}
