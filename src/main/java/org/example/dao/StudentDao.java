package org.example.dao;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Department;
import org.example.domain.PWEncoder;
import org.example.domain.Student;
import org.example.mapper.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Component
public class StudentDao {
    JdbcTemplate jdbcTemplate;
    StudentRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PWEncoder pwEncoder;
    private static List<Student> students ;

//    static {
//        students = new ArrayList<>();
//        students.add(new Student(1, "user1", "pass1","test@123"));
//        students.add(new Student(2, "user2", "pass2","test@123"));
//        students.add(new Student(3, "user3", "pass3","test@123"));
//    }


    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper rowMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        students=getAllUsers();
    }


    public void createNewStudent(Student student){
        String query = "INSERT INTO student(username, encrypted_password,email,first_name,last_name,dept_id,is_active,is_admin) values (?, ? , ? ,?,?,?,?,?)";
        jdbcTemplate.update(query, student.getUsername(), student.getEncrypted_password(),
                student.getEmail(),student.getFirstName(),student.getLastName(),student.getDept_id(),true,false);
    }

    public List<Student> getAllUsers(){
        String query = "SELECT * FROM student";

        List<Student> students = jdbcTemplate.query(query, rowMapper);

        return students;
    }

    public List<Student> getAllStudents(){
        String query = "SELECT * FROM student where is_admin=false;";

        List<Student> students = jdbcTemplate.query(query, rowMapper);

        return students;
    }



    public Optional<Student> validateLogin(String username, String password) {
        return students.stream()
                .filter(a -> a.getUsername().equals(username)
                        && bCryptPasswordEncoder.matches(password,a.getEncrypted_password()) )
                .findAny();
    }

    public Student getStudentById(int studentId) {

        String query = "SELECT * FROM student where  id = ?";

        List<Student> students = jdbcTemplate.query(query,rowMapper,studentId);

        return students.size() == 0 ? null : students.get(0);
    }

    public void activateStudentById(int studentId) {

        String query = "Update student set is_active=1 where id=?";
        jdbcTemplate.update(query, studentId );
    }

    public void deactivateStudentById(int studentId) {

        String query = "Update student set is_active=0 where id=?";
        jdbcTemplate.update(query, studentId );
    }

    public int getTotalPagesAdmin(int limit) {

        String query = "SELECT * FROM student where is_admin=false;";

        List<Student> students = jdbcTemplate.query(query, rowMapper);
        if(students==null) return 0;
        if(students.size()%limit==0) return students.size()/limit;
        else return students.size()/limit+1;

    }

    public List<Student> getStudentsByPageLimit(int page, int limit) {
        String query = "SELECT * FROM student where is_admin=false order by id asc limit ?,?;";

        List<Student> students = jdbcTemplate.query(query, rowMapper,(page-1)*limit,limit);

        return students;
    }
}
