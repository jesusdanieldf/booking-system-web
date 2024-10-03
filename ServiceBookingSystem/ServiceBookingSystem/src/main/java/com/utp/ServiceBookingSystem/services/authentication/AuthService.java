package com.utp.ServiceBookingSystem.services.authentication;

import com.utp.ServiceBookingSystem.dto.SignupRequestDTO;
import com.utp.ServiceBookingSystem.dto.UserDto;

public interface AuthService {

    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    Boolean presentByEmail(String email); //mencionado de AuthServiceImpl

    UserDto signupCompany(SignupRequestDTO signupRequestDTO);
}
