package org.example.mapper;


import org.example.domain.Classroom;
import org.example.domain.WebRegClass;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WebRegClassRowMapper implements RowMapper<WebRegClass> {
    @Override
    public WebRegClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        WebRegClass webRegClass = new WebRegClass();
        webRegClass.setId(rs.getInt("id"));
        webRegClass.setCourse_id(rs.getInt("course_id"));
        webRegClass.setProfessor_id(rs.getInt("professor_id"));
        webRegClass.setCapacity(rs.getInt("capacity"));
        webRegClass.setClassroom_id(rs.getInt("classroom_id"));
        webRegClass.setSemester_id(rs.getInt("semester_id"));
        webRegClass.setLecture_id(rs.getInt("lecture_id"));
        webRegClass.set_active(rs.getBoolean("is_active"));
        return webRegClass;
    }
}

