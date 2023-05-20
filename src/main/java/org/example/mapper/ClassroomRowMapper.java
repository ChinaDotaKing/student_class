package org.example.mapper;


import org.example.domain.Classroom;
import org.example.domain.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassroomRowMapper implements RowMapper<Classroom> {
    @Override
    public Classroom mapRow(ResultSet rs, int rowNum) throws SQLException {
        Classroom classroom = new Classroom();
        classroom.setId(rs.getInt("id"));
        classroom.setName(rs.getString("name"));
        classroom.setBuilding(rs.getString("building"));
        classroom.setCapacity(rs.getInt("capacity"));


        return classroom;
    }
}

