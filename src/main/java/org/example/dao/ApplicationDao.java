package org.example.dao;


import lombok.Getter;
import lombok.Setter;
import org.example.domain.Application;
import org.example.domain.Classroom;
import org.example.mapper.ApplicationRowMapper;
import org.example.mapper.ClassroomRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@Component
public class ApplicationDao {
    JdbcTemplate jdbcTemplate;
    ApplicationRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public ApplicationDao(JdbcTemplate jdbcTemplate, ApplicationRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    public Application getApplicationById(int id){
        String query = "SELECT * FROM application where id = ?";

       List<Application> application = jdbcTemplate.query(query,rowMapper,id);

        return application.size() == 0 ? null : application.get(0);


    }

    public void createApplicationByStudentClassId(int id, int classId) {
        String query = "INSERT INTO application(student_id, class_id,creation_time,request,status,feedback) value (?, ? ,CURRENT_TIMESTAMP(), ? ,?,?)";
        jdbcTemplate.update(query, id, classId, "Add","Pending" , "" );
    }


    public List<Application> getApplicationByStudentId(int studentId) {

        String query = "SELECT * FROM application where student_id = ? order by creation_time desc;";

        List<Application> application = jdbcTemplate.query(query,rowMapper,studentId);

        return application.size() == 0 ? null :application;
    }

    public List<Application> getPendingApplications() {

        String query = "SELECT * FROM application where status=? order by creation_time desc;";

        List<Application> application = jdbcTemplate.query(query,rowMapper,"Pending");

        return application.size() == 0 ? null :application;
    }

    public List<Application> getAllApplications() {

        String query = "SELECT * FROM application order by creation_time desc;";

        List<Application> application = jdbcTemplate.query(query,rowMapper);

        return application.size() == 0 ? null :application;
    }

    public void rejectOrApproveByIdFeedback(String status, int applicationId, String f) {

        String query = "Update application set status=?,feedback=? where id=?";
        jdbcTemplate.update(query, status,f,applicationId );


    }

    public String getRequestByApplicationId(int applicationId) {

        String query = "SELECT * FROM application where id=?";

        List<Application> application = jdbcTemplate.query(query,rowMapper,applicationId);

        return application.size() == 0 ? null :application.get(0).getRequest();
    }

    public int getStudentIdByApplicationId(int applicationId) {
        String query = "SELECT * FROM application where id=?";

        List<Application> application = jdbcTemplate.query(query,rowMapper,applicationId);

        return application.size() == 0 ? null :application.get(0).getStudent_id();

    }

    public int getClassIdByApplicationId(int applicationId) {
        String query = "SELECT * FROM application where id=?";

        List<Application> application = jdbcTemplate.query(query,rowMapper,applicationId);

        return application.size() == 0 ? null :application.get(0).getClass_id();

    }

    public void createEnrollmentApplication(int studentId, int classId) {

        String query = "INSERT INTO application(student_id, class_id,creation_time,request,status,feedback) value (?, ? ,CURRENT_TIMESTAMP(), ? ,?,?)";
        jdbcTemplate.update(query, studentId, classId, "Withdraw","Pending" , "" );
    }

    public void withdrawApplication(int applicationId) {

        String query = "Update application set status='Withdraw' where id=?";
        jdbcTemplate.update(query, applicationId );
    }

    public void resubmitApplication(int applicationId) {

        String query = "Update application set status='Pending' where id=?";
        jdbcTemplate.update(query, applicationId );
    }
}
