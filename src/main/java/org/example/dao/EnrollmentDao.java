package org.example.dao;


import lombok.Getter;
import lombok.Setter;
import org.example.domain.Enrollment;
import org.example.mapper.EnrollmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@Component
public class EnrollmentDao {
    JdbcTemplate jdbcTemplate;
    EnrollmentRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public EnrollmentDao(JdbcTemplate jdbcTemplate, EnrollmentRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Enrollment getEnrollmentById(int id){
        String query = "SELECT * FROM enrollment where  id = ?;";

       List<Enrollment> enrollment = jdbcTemplate.query(query,rowMapper,id);

        return enrollment.size() == 0 ? null : enrollment.get(0);


    }

   public List<Enrollment> getActiveEnrollmentsByClassId(int classId){
       String query = "SELECT * FROM enrollment where class_id = ? and status='ongoing';";

       List<Enrollment> enrollment = jdbcTemplate.query(query,rowMapper,classId);

       return enrollment.size() == 0 ? null : enrollment;
   }


   public boolean getEnrollmentStatus(int studentId,int classId){
       String query = "SELECT * FROM enrollment where student_id = ? and class_Id=? and status='ongoing';";

       List<Enrollment> enrollment = jdbcTemplate.query(query,rowMapper,studentId,classId);

       return enrollment.size() == 0 ? false : true;
   }

    public String getStatus(int studentId,int classId){
        String query = "SELECT * FROM enrollment where student_id = ? and class_Id=? ;";

        List<Enrollment> enrollment = jdbcTemplate.query(query,rowMapper,studentId,classId);

        return enrollment.size() == 0 ? null : enrollment.get(0).getStatus();
    }

    public List<Enrollment> getClassesByStudentId(int id) {
        String query = "SELECT * FROM enrollment where student_id = ? and (status='pass' or status='ongoing')";

        List<Enrollment> enrollment = jdbcTemplate.query(query,rowMapper,id);

        return enrollment.size() == 0 ? null : enrollment;

    }

    public int getEnrollmentNumByClassId(int id) {
        String query = "SELECT * FROM enrollment where class_id = ? and status='ongoing'";

        List<Enrollment> enrollment = jdbcTemplate.query(query,rowMapper,id);
        return enrollment==null?0:enrollment.size();
    }

    public void addEnrollment(int studentId,int classId){
        String query = "INSERT INTO enrollment(student_id,class_id,status) value(?,?,?);";

        jdbcTemplate.update(query,studentId,classId, "ongoing");
        return ;
    }

    public void dropEnrollment(int studentId,int classId){
        String query = "delete from enrollment where student_id=? and class_id=?";

        jdbcTemplate.update(query,studentId,classId);
        return ;
    }

    public void withdrawEnrollment(int studentId, int classId) {
        String query = "update enrollment set status='withdraw' where student_id=? and class_id=?";

        jdbcTemplate.update(query,studentId,classId);
        return ;

    }

    public List<Enrollment> getEnrollmentsByStudentId(int studentId){
        String query = "SELECT * FROM enrollment where student_id=?";

        List<Enrollment> enrollment = jdbcTemplate.query(query,rowMapper,studentId);
        return enrollment==null?null:enrollment;
    }

    public void failStudentByClassId(int studentId, int classId) {

        String query = "update enrollment set status='fail' where student_id=? and class_id=?";

        jdbcTemplate.update(query,studentId,classId);
    }

    public void passStudentByClassId(int studentId, int classId) {

        String query = "update enrollment set status='pass' where student_id=? and class_id=?";

        jdbcTemplate.update(query,studentId,classId);
    }


}
