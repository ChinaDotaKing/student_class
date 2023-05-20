package org.example.mapper;


import org.example.domain.Classroom;
import org.example.domain.Course;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setCourse_code(rs.getString("course_code"));
        course.setCourse_name(rs.getString("course_name"));
        course.setDept_id(rs.getInt("dept_id"));
        course.setDescription(rs.getString("description"));


        return course;
    }
}

