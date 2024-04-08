package com.csye6220.eduverse.service;

import com.csye6220.eduverse.mapper.RegistrationMapper;
import com.csye6220.eduverse.pojo.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    RegistrationMapper registrationMapper;

    @Autowired
    public RegistrationServiceImpl(RegistrationMapper registrationMapper) {
        this.registrationMapper = registrationMapper;
    }

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {
        if("student".equals(registrationDTO.getRole())) {
            registrationMapper.mapRegistrationToStudent(registrationDTO);
        } else if("instructor".equals(registrationDTO.getRole())) {
            registrationMapper.mapRegistrationToInstructor(registrationDTO);
        }
    }
}
