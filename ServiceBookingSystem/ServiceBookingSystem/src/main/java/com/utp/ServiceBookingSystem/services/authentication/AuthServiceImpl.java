package com.utp.ServiceBookingSystem.services.authentication;

import com.utp.ServiceBookingSystem.dto.SignupRequestDTO;
import com.utp.ServiceBookingSystem.dto.UserDto;
import com.utp.ServiceBookingSystem.entity.User;
import com.utp.ServiceBookingSystem.enums.UserRole;
import com.utp.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserDto signupClient(SignupRequestDTO signupRequestDTO){
        User user = new User();

        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(signupRequestDTO.getPassword()); //a futuro encriptar password en DB

        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }
}
