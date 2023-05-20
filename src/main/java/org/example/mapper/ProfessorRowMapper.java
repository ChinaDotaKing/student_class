package org.example.mapper;

import org.example.domain.Department;
import org.example.domain.Professor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class ProfessorRowMapper implements RowMapper<Professor> {
    @Override
    public Professor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Professor professor = new Professor();
        professor.setId(rs.getInt("id"));
        professor.setFirstName(rs.getString("firstName"));
        professor.setLastName(rs.getString("lastName"));
        professor.setEmail(rs.getString("email"));
        professor.setDept_id(rs.getInt("dept_id"));



        return professor;
    }
}

