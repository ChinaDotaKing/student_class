package org.example.dao;


import lombok.Getter;
import lombok.Setter;
import org.example.domain.Classroom;
import org.example.domain.Prerequisite;
import org.example.mapper.ClassroomRowMapper;
import org.example.mapper.PrerequisiteRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@Component
public class PrerequisiteDao {
    JdbcTemplate jdbcTemplate;
    PrerequisiteRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public PrerequisiteDao(JdbcTemplate jdbcTemplate, PrerequisiteRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Prerequisite getPrerequisiteById(int id){
        String query = "SELECT * FROM Prerequisite where  id = ?";

       List<Prerequisite> prerequisite = jdbcTemplate.query(query,rowMapper,id);

        return prerequisite.size() == 0 ? null : prerequisite.get(0);


    }

    public List<Prerequisite> getPrerequisiteByCourseId(int id) {

        String query = "SELECT * FROM Prerequisite where  course_id = ?";

        List<Prerequisite> prerequisite = jdbcTemplate.query(query,rowMapper,id);

        return prerequisite.size() == 0 ? null : prerequisite;
    }
}
