package com.csye6220.eduverse.dao;

import java.util.List;

import com.csye6220.eduverse.entity.Course;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author kai
 */
@Component
public class CourseDAO extends DAO {

    public List<Course> getCoursesByDepartment(Long departmentID) {
        begin();

        String hql = "FROM Course c WHERE c.department.id = :departmentID";
        Query query = getSession().createQuery(hql);
        query.setParameter("departmentID", departmentID);

        List<Course> courses = query.list();
        System.out.println(courses);
        commit();
        return courses;
    }
}