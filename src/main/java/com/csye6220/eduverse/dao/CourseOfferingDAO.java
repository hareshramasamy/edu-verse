package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.CourseOffering;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseOfferingDAO extends DAO {
    public List<CourseOffering> getEnrollmentsByInstructor(String username) {
        begin();
        String hql = "FROM CourseOffering c WHERE c.instructor.user.username = :username";
        Query<CourseOffering> query = getSession().createQuery(hql, CourseOffering.class);
        query.setParameter("username", username);

        List<CourseOffering> courseOfferings = query.list();
        System.out.println(courseOfferings);
        commit();
        close();
        return courseOfferings;
    }

    public void createCourseOffering(CourseOffering courseOffering) {
        begin();
        getSession().persist(courseOffering);
        commit();
        close();
    }

    public CourseOffering getCourseOfferingById(Long courseOfferingId) {
        begin();
        String hql = "FROM CourseOffering c WHERE c.id = :id";
        Query<CourseOffering> query = getSession().createQuery(hql, CourseOffering.class);
        query.setParameter("id", courseOfferingId);

        CourseOffering courseOffering = query.uniqueResult();
        System.out.println(courseOffering);
        commit();
        close();
        return courseOffering;
    }
}
