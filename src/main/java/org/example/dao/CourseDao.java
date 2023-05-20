package org.example.dao;


import lombok.Getter;
import lombok.Setter;
import org.example.domain.Classroom;
import org.example.domain.Course;
import org.example.domain.WebRegClass;
import org.example.mapper.ClassroomRowMapper;
import org.example.mapper.CourseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@Component
public class CourseDao {
    JdbcTemplate jdbcTemplate;
    CourseRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public CourseDao(JdbcTemplate jdbcTemplate, CourseRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Course getCourseById(int id){
        String query = "SELECT * FROM course where  id = ?";

       List<Course> courses = jdbcTemplate.query(query,rowMapper,id);

        return courses.size() == 0 ? null : courses.get(0);


    }

    public List<Course> getAllCourses(){
        String query = "SELECT * FROM course;";

        List<Course> courses = jdbcTemplate.query(query,rowMapper);

        return courses.size() == 0 ? null : courses;
    }

    public void createNewCourse(Course course){
        String query = "INSERT INTO course(course_name, course_code,dept_id,description) values (?, ? , ? ,?)";
        jdbcTemplate.update(query, course.getCourse_name(), course.getCourse_code(),
                course.getDept_id(),course.getDescription());
    }

    public String getCourseNameById(int courseId) {
        String query = "SELECT * FROM course where  id = ?";

        List<Course> courses = jdbcTemplate.query(query,rowMapper,courseId);

        return courses.size() == 0 ? null : courses.get(0).getCourse_name();
    }


}
