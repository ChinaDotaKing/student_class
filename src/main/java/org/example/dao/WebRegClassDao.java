package org.example.dao;


import lombok.Getter;
import lombok.Setter;
import org.example.domain.WebRegClass;
import org.example.mapper.WebRegClassRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Getter
@Setter
@Repository
public class WebRegClassDao {
    JdbcTemplate jdbcTemplate;
    WebRegClassRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public WebRegClassDao(JdbcTemplate jdbcTemplate, WebRegClassRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public WebRegClass getWebRegClassById(int id){
        String query = "SELECT * FROM webRegClass where  id = ?";

       List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper,id);

        return webRegClasses.size() == 0 ? null : webRegClasses.get(0);

    }

    public List<WebRegClass> getWebRegClassesByCourseId(int id){
        String query = "SELECT * FROM WebRegClass where course_id = ?";

        List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper,id);

        return webRegClasses.size() == 0 ? null : webRegClasses;

    }


    public List<WebRegClass> getWebRegClassesByStudentId(int id){
        String query = "SELECT * FROM WebRegClass w where w.id in( select class_id from student left join enrollment on enrollment.student_id = student.id where student.id=? and status='ongoing') ";

        List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper,id);

        return webRegClasses.size() == 0 ? null : webRegClasses;

    }


    public List<WebRegClass> getAllActiveClasses(){
        String query = "select w3.* from (select * from WebRegClass w2 where CURDATE() < all ( select end_date from semester where w2.semester_id=semester.id )) w3 join semester on w3.semester_id=semester.id where w3.is_active=1 order by start_date asc; ";
        List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper);

        return webRegClasses.size() == 0 ? null : webRegClasses;

    }

    public List<WebRegClass> getAllClasses(){
        String query = "select w3.* from WebRegClass w3 join semester on w3.semester_id=semester.id order by start_date desc; ";
        List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper);

        return webRegClasses.size() == 0 ? null : webRegClasses;

    }


    public List<WebRegClass> getAllStatusClassesByStudentId(int studentId) {
        String query = "SELECT * FROM WebRegClass w where w.id in( select class_id from student left join enrollment on enrollment.student_id = student.id where student.id=?) ";
        List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper,studentId);

        return webRegClasses.size() == 0 ? null : webRegClasses;
    }


    public void createNewClass(WebRegClass theClass){
        String query = "INSERT INTO WebRegClass(course_id, professor_id,semester_id,classroom_id,capacity) values (?, ? , ? ,?,?)";
        jdbcTemplate.update(query, theClass.getCourse_id(), theClass.getProfessor_id(),
                theClass.getSemester_id(),theClass.getClassroom_id() ,theClass.getCapacity());
    }

    public void deactivateClassById(int classId) {
        String query = "update WebRegClass set is_active=? where id=?;";
        jdbcTemplate.update(query, 0,classId);
    }
    public void activateClassById(int classId) {
        String query = "update WebRegClass set is_active=? where id=?;";
        jdbcTemplate.update(query, 1,classId);
    }

    public List<WebRegClass> getWebRegClassesByStudentIdPageLimit(int id, int page, int limit) {

        String query = "select w2.* from (SELECT * FROM WebRegClass w where w.id in( select class_id from student left join enrollment on enrollment.student_id = student.id where student.id=? )) w2 left join semester on semester.id=w2.semester_id order by start_date limit ?,?;  ";

        List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper,id,(page-1)*limit,limit);

        return webRegClasses.size() == 0 ? null : webRegClasses;
    }

    public WebRegClass getClassesbyStudentIdEnrollmentIdPageLimit(int studentId, int enrollmentId, int page, int limit) {

        String query = "select w2.* from (SELECT * FROM WebRegClass w where w.id in( select class_id from student left join enrollment on enrollment.student_id = student.id where student.id=? and enrollment.id=? )) w2 left join semester on semester.id=w2.semester_id order by start_date limit ?,?;  ";

        List<WebRegClass> webRegClasses = jdbcTemplate.query(query,rowMapper,studentId,enrollmentId,(page-1)*limit,limit);

        return webRegClasses.size() == 0 ? null : webRegClasses.get(0);
    }
}
