package org.example.dao;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Professor;
import org.example.domain.Semester;
import org.example.mapper.ProfessorRowMapper;
import org.example.mapper.SemesterRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Getter
@Setter
@Component
public class SemesterDao {


    JdbcTemplate jdbcTemplate;
    SemesterRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public SemesterDao(JdbcTemplate jdbcTemplate, SemesterRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Semester getSemesterById(int id){
        String query = "SELECT * FROM Semester where  id = ?";

        List<Semester> semesters = jdbcTemplate.query(query,rowMapper,id);

        return semesters.size() == 0 ? null : semesters.get(0);


    }
}
