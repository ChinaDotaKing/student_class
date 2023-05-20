package org.example.dao;


import lombok.Getter;
import lombok.Setter;
import org.example.domain.Classroom;
import org.example.domain.Student;
import org.example.mapper.ClassroomRowMapper;
import org.example.mapper.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@Component
public class ClassroomDao {
    JdbcTemplate jdbcTemplate;
    ClassroomRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public ClassroomDao(JdbcTemplate jdbcTemplate, ClassroomRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Classroom getClassroomById(int id){
        String query = "SELECT * FROM classroom where  id = ?";

       List<Classroom> classroom = jdbcTemplate.query(query,rowMapper,id);

        return classroom.size() == 0 ? null : classroom.get(0);


    }
}
