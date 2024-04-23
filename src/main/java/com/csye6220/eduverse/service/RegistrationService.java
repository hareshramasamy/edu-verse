package com.csye6220.eduverse.service;

import com.csye6220.eduverse.pojo.RegistrationDTO;
import com.csye6220.eduverse.pojo.TestRegistrationDTO;

public interface RegistrationService {
    void registerUser(RegistrationDTO registrationDTO);

    void registerUserTest(TestRegistrationDTO registrationDTO);
}
