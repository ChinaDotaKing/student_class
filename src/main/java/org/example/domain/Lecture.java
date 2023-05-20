package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Time;

enum WeekDay{
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday
}

@Getter
@Setter
@Component
public class Lecture {


    private int id;
    private int day_of_week;
    private Time start_time;
    private Time end_time;
}
