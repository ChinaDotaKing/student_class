package org.example.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;

enum Request{
    Add,
    Withdraw
}


enum Application_Status{
    ongoing,
    pass,
    fail,
    withdraw
}

@Getter
@Setter
@Component
public class Application {

    private int id;
    private int student_id;
    private int class_id;
    private Timestamp creation_time;
    private String request;

    private String status;
    private String feedback;

}
