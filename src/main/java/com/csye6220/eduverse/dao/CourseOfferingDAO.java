package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.CourseOffering;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseOfferingDAO extends DAO {
    public List<CourseOffering> getEnrollmentsByStudent(String username) {
        begin();
        String hql = "FROM CourseOffering c WHERE c.instructor.user.username = :username";
        Query query = getSession().createQuery(hql);
        query.setParameter("username", username);

        List<CourseOffering> courseOfferings = query.list();
        System.out.println(courseOfferings);
        commit();
        return courseOfferings;
    }
}
