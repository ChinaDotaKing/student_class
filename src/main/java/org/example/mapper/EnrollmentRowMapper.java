package org.example.mapper;

import org.example.domain.Department;
import org.example.domain.Enrollment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class EnrollmentRowMapper implements RowMapper<Enrollment> {
    @Override
    public Enrollment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(rs.getInt("id"));
        enrollment.setStudent_id(rs.getInt("student_id"));
        enrollment.setClass_id(rs.getInt("class_id"));
        enrollment.setStatus(rs.getString("status"));



        return enrollment;
    }
}

