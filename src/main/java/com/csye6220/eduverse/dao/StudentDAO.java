package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO extends DAO {
    public void saveStudent(Student student) {
        begin();
        getSession().persist(student);
        commit();
        close();
    }
}
