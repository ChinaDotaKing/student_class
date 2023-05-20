package org.example.mapper;


import org.example.domain.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setUsername(rs.getString("username"));
        student.setEncrypted_password(rs.getString("encrypted_password"));
        student.setEmail(rs.getString("email"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setDept_id(rs.getInt("dept_id"));
        student.set_active(rs.getBoolean("is_active"));
        student.set_admin(rs.getBoolean("is_admin"));


        return student;
    }
}

