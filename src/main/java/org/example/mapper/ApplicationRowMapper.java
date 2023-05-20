package org.example.mapper;


import org.example.domain.Application;
import org.example.domain.Classroom;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ApplicationRowMapper implements RowMapper<Application> {
    @Override
    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
        Application application = new Application();
        application.setId(rs.getInt("id"));
        application.setClass_id(rs.getInt("class_id"));
        application.setStudent_id(rs.getInt("student_id"));
        application.setRequest(rs.getString("request"));
        application.setStatus(rs.getString("status"));
        application.setFeedback(rs.getString("feedback"));
        application.setCreation_time(rs.getTimestamp("creation_time"));


        return application;
    }
}

