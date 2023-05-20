package org.example.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class Enrollment {

    private int id;
    private int student_id;
    private int class_id;
    private String status;


}
