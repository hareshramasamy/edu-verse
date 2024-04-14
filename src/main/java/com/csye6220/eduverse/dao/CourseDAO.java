package com.csye6220.eduverse.dao;

import java.util.List;

import com.csye6220.eduverse.entity.Course;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kai
 */
@Repository
public class CourseDAO extends DAO {

    public List<Course> getCoursesByDepartment(Long departmentID) {
        begin();

        String hql = "FROM Course c WHERE c.department.id = :departmentID";
        Query<Course> query = getSession().createQuery(hql, Course.class);
        query.setParameter("departmentID", departmentID);

        List<Course> courses = query.list();
        System.out.println(courses);
        close();
        return courses;
    }

    public Course getCourseById(Long id) {
        begin();

        String hql = "FROM Course c WHERE c.id = :id";
        Query<Course> query = getSession().createQuery(hql, Course.class);
        query.setParameter("id", id);

        Course course = query.uniqueResult();
        System.out.println(course);
        close();
        return course;
    }
}