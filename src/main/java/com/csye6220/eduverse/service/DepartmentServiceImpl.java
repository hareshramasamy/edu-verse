package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.DepartmentDAO;
import com.csye6220.eduverse.mapper.DepartmentMapper;
import com.csye6220.eduverse.pojo.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;

    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentDAO departmentDAO, DepartmentMapper departmentMapper) {
        this.departmentDAO = departmentDAO;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentDAO.getAllDepartments()
                .stream()
                .map(department -> departmentMapper.mapDepartmentToDTO(department))
                .collect(Collectors.toList());
    }
}
