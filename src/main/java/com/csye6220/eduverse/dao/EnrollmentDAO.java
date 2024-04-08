package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Enrollment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentDAO extends DAO {

    public List<Enrollment> getEnrollmentsByStudent(String username) {
        begin();
        String hql = "FROM Enrollment e WHERE e.student.user.username = :username";
        Query query = getSession().createQuery(hql);
        query.setParameter("username", username);

        List<Enrollment> enrollments = query.list();
        System.out.println(enrollments);
        commit();
        return enrollments;
    }
}

