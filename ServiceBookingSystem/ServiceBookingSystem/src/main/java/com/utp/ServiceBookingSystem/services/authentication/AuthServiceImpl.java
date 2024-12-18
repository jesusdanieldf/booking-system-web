package com.utp.ServiceBookingSystem.services.authentication;

import com.utp.ServiceBookingSystem.dto.SignupRequestDTO;
import com.utp.ServiceBookingSystem.dto.UserDto;
import com.utp.ServiceBookingSystem.entity.User;
import com.utp.ServiceBookingSystem.enums.UserRole;
import com.utp.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        //user.setPassword(signupRequestDTO.getPassword()); //a futuro encriptar password en DB
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));


        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }

    public Boolean presentByEmail(String email){
        return userRepository.findFirstByEmail(email) != null;//true si tenemos al usuario
    }

    public UserDto signupCompany(SignupRequestDTO signupRequestDTO){
        User user = new User();

        user.setName(signupRequestDTO.getName());
        //user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        //user.setPassword(signupRequestDTO.getPassword()); //a futuro encriptar password en DB
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.COMPANY);

        return userRepository.save(user).getDto();
    }
//Pruebas:

    /*public UserDto addTestUser() {
        User user = new User();
        user.setName("Test");
        user.setLastname("User");
        user.setEmail("prueba_sprint2@example.com");
        user.setPhone("123456789");
        user.setPassword("password123");
        user.setRole(UserRole.CLIENT);

        User savedUser = userRepository.save(user); // Esto guardará el usuario en la base de datos

        return savedUser.getDto(); // Retorna el DTO con los datos guardados
    }*/

}
