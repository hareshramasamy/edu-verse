package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.entity.Department;
import com.csye6220.eduverse.pojo.DepartmentDTO;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentDTO mapDepartmentToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setDepartmentCode(department.getDepartmentCode());
        departmentDTO.setDepartmentName(department.getDepartmentName());
        return departmentDTO;
    }

}
