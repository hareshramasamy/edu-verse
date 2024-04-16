package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Instructor;
import com.csye6220.eduverse.entity.Student;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class StudentDAO extends DAO {
    public void saveStudent(Student student) {
        begin();
        getSession().persist(student);
        commit();
        close();
    }

    public Student getStudentByUsername(String username) {
        begin();
        String hql = "FROM Student s WHERE s.user.username = :username";
        Query<Student> query = getSession().createQuery(hql, Student.class);
        query.setParameter("username", username);
        Student studentResult = query.uniqueResult();
        System.out.println(Objects.nonNull(studentResult) ? studentResult.getUser().getUsername(): "Student is not found");
        close();
        return studentResult;
    }

    public Student getStudentByEmail(String email) {
        begin();
        String hql = "FROM Student s WHERE s.user.email = :email";
        Query<Student> query = getSession().createQuery(hql, Student.class);
        query.setParameter("email", email);
        Student studentResult = query.uniqueResult();
        System.out.println(Objects.nonNull(studentResult) ? studentResult.getUser().getEmail(): "Student is not found");
        close();
        return studentResult;
    }
}
