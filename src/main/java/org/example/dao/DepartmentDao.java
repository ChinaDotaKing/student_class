package org.example.dao;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Classroom;
import org.example.domain.Department;
import org.example.mapper.ClassroomRowMapper;
import org.example.mapper.DepartmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class DepartmentDao {

    JdbcTemplate jdbcTemplate;
    DepartmentRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public DepartmentDao(JdbcTemplate jdbcTemplate, DepartmentRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Department getDepartmentById(int id){
        String query = "SELECT * FROM department where  id = ?";

        List<Department> department = jdbcTemplate.query(query,rowMapper,id);

        return department.size() == 0 ? null : department.get(0);


    }
}
