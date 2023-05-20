package org.example.dao;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Classroom;
import org.example.domain.Lecture;
import org.example.mapper.ClassroomRowMapper;
import org.example.mapper.LectureRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Getter
@Setter
@Component
public class LectureDao {

    JdbcTemplate jdbcTemplate;
    LectureRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public LectureDao(JdbcTemplate jdbcTemplate, LectureRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Lecture getLectureById(int id){
        String query = "SELECT * FROM lecture where  id = ?";

        List<Lecture> lectures = jdbcTemplate.query(query,rowMapper,id);

        return lectures.size() == 0 ? null : lectures.get(0);


    }

    public Lecture getLectureByClassId(int id){
        String query = "SELECT * FROM lecture where  class_id = ?";

        List<Lecture> lectures = jdbcTemplate.query(query,rowMapper,id);

        return lectures.size() == 0 ? null : lectures.get(0);
    }
}
