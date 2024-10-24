package com.utp.ServiceBookingSystem.controller;

import com.utp.ServiceBookingSystem.dto.SignupRequestDTO;
import com.utp.ServiceBookingSystem.dto.UserDto;
import com.utp.ServiceBookingSystem.services.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.ResponseBody;

//@RequestMapping("/auth") //mapeo de prueba
@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    //Creacion de endpoint para Cliente Sign-up
    @PostMapping("/client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO){

        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Ya existe un cliente con este email", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto createdUser = authService.signupClient(signupRequestDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    //Creacion de endpoint para Company Sign-up
    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO){

        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Ya existe un compañía con este email", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto createdUser = authService.signupClient(signupRequestDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    // Endpoint temporal para agregar un usuario de prueba
    /*@GetMapping("/add-test-user")
    public ResponseEntity<UserDto> addTestUser() {
        UserDto userDto = authService.addTestUser();
        return ResponseEntity.ok(userDto); // Devuelve el usuario guardado
    }*/
}
