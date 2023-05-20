package org.example.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Prerequisite {
    private int id;
    private int course_id;
    private int pre_req_id;
}
