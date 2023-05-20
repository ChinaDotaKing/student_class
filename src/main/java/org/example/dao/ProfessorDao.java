package org.example.dao;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Lecture;
import org.example.domain.Professor;
import org.example.mapper.LectureRowMapper;
import org.example.mapper.ProfessorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Getter
@Setter
@Component
public class ProfessorDao {


    JdbcTemplate jdbcTemplate;
    ProfessorRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public ProfessorDao(JdbcTemplate jdbcTemplate, ProfessorRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Professor getProfessorById(int id){
        String query = "SELECT * FROM Professor where  id = ?";

        List<Professor> professor = jdbcTemplate.query(query,rowMapper,id);

        return professor.size() == 0 ? null : professor.get(0);


    }
}
