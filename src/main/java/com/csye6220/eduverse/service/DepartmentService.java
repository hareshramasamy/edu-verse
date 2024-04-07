package com.csye6220.eduverse.service;

import com.csye6220.eduverse.entity.Department;
import com.csye6220.eduverse.pojo.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
}
