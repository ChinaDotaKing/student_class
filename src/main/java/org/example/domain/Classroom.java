package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Getter
@Setter
@Component
public class Classroom {


    private int id;
    private String name;
    private String building;
    private int capacity;

}
