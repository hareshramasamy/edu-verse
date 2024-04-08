package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Department;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DepartmentDAO extends DAO {

    public List<Department> getAllDepartments() {
        begin();

        String hql = "FROM Department e";
        Query query = getSession().createQuery(hql);

        List<Department> departments = query.list();
        System.out.println(departments);
        commit();
        return departments;
    }

    public Department getDepartmentById(Long id) {
        begin();

        String hql = "FROM Department d WHERE d.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);

        Department department = (Department) query.uniqueResult();
        System.out.println(Objects.nonNull(department)?department.getDepartmentName(): "Department is not found");
        close();
        return department;
    }
}