package org.example.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Getter
@Setter
@Component
public class Semester {
    private int id;
    private String name;
    private Date start_date;
    private Date end_date;
}
