package org.example.mapper;


import org.example.domain.Lecture;
import org.example.domain.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LectureRowMapper implements RowMapper<Lecture> {
    @Override
    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lecture lecture = new Lecture();
        lecture.setId(rs.getInt("id"));
        lecture.setDay_of_week(rs.getInt("day_of_week"));
        lecture.setStart_time(rs.getTime("start_time"));
        lecture.setEnd_time(rs.getTime("end_time"));


        return lecture;
    }
}

