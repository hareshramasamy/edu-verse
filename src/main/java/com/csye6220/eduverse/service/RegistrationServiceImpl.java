package com.csye6220.eduverse.service;

import com.csye6220.eduverse.mapper.RegistrationMapper;
import com.csye6220.eduverse.pojo.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationMapper registrationMapper;

    @Autowired
    public RegistrationServiceImpl(RegistrationMapper registrationMapper) {
        this.registrationMapper = registrationMapper;
    }

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {
        formatUserFullName(registrationDTO);
        if("student".equals(registrationDTO.getRole())) {
            registrationMapper.mapRegistrationToStudent(registrationDTO);
        } else if("instructor".equals(registrationDTO.getRole())) {
            registrationMapper.mapRegistrationToInstructor(registrationDTO);
        }
    }

    private void formatUserFullName(RegistrationDTO registrationDTO) {
        if(registrationDTO.getFirstName() != null && !registrationDTO.getFirstName().isEmpty()) {
            String firstName = registrationDTO.getFirstName();
            registrationDTO.setFirstName(Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1).toLowerCase());
        }
        if(registrationDTO.getLastName() != null && !registrationDTO.getLastName().isEmpty()) {
            String lastName = registrationDTO.getLastName();
            registrationDTO.setLastName(Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1).toLowerCase());
        }
    }
}
