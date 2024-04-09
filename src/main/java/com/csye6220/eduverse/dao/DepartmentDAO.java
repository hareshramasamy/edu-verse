package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Department;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class DepartmentDAO extends DAO {

    public List<Department> getAllDepartments() {
        begin();

        String hql = "FROM Department e";
        Query<Department> query = getSession().createQuery(hql, Department.class);

        List<Department> departments = query.list();
        System.out.println(departments);
        close();
        return departments;
    }

    public Department getDepartmentById(Long id) {
        begin();

        String hql = "FROM Department d WHERE d.id = :id";
        Query<Department> query = getSession().createQuery(hql, Department.class);
        query.setParameter("id", id);

        Department department = query.uniqueResult();
        System.out.println(Objects.nonNull(department)?department.getDepartmentName(): "Department is not found");
        close();
        return department;
    }
}