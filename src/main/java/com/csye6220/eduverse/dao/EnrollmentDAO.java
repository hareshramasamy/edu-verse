package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Enrollment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentDAO extends DAO {

    public List<Enrollment> getEnrollmentsByStudent(String username) {
        begin();
        String hql = "FROM Enrollment e WHERE e.student.user.username = :username";
        Query<Enrollment> query = getSession().createQuery(hql, Enrollment.class);
        query.setParameter("username", username);

        List<Enrollment> enrollments = query.list();
        System.out.println(enrollments);
        close();
        return enrollments;
    }

    public boolean checkCurrentCourseEnrollment(Long courseOfferingId, String username) {
        begin();
        String hql = "SELECT COUNT(e) FROM Enrollment e WHERE e.courseOffering.id = :courseOfferingId AND e.student.user.username = :username";
        Query<Long> query = getSession().createQuery(hql, Long.class);
        query.setParameter("courseOfferingId", courseOfferingId);
        query.setParameter("username", username);

        Long count = query.getSingleResult();
        close();
        return count != 0;
    }

    public List<Enrollment> getEnrollmentsByCourseOfferingId(Long courseOfferingId) {
        begin();
        String hql = "FROM Enrollment e WHERE e.courseOffering.id = :courseOfferingId";
        Query<Enrollment> query = getSession().createQuery(hql, Enrollment.class);
        query.setParameter("courseOfferingId", courseOfferingId);
        List<Enrollment> enrollments = query.list();
        System.out.println(enrollments);
        close();
        return enrollments;
    }
}

