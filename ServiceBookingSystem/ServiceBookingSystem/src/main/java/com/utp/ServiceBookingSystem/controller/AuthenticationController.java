package com.utp.ServiceBookingSystem.controller;

import com.utp.ServiceBookingSystem.dto.AuthenticationRequest;
import com.utp.ServiceBookingSystem.dto.SignupRequestDTO;
import com.utp.ServiceBookingSystem.dto.UserDto;
import com.utp.ServiceBookingSystem.entity.User;
import com.utp.ServiceBookingSystem.repository.UserRepository;
import com.utp.ServiceBookingSystem.services.authentication.AuthService;
import com.utp.ServiceBookingSystem.services.jwt.UserDetailsServiceImpl;
import com.utp.ServiceBookingSystem.util.JwUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
//import org.springframework.web.bind.annotation.ResponseBody;

//@RequestMapping("/auth") //mapeo de prueba
@RestController
public class AuthenticationController {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwUtil jwUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public AuthenticationController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


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

        UserDto createdUser = authService.signupCompany(signupRequestDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    // Endpoint temporal para agregar un usuario de prueba
    /*@GetMapping("/add-test-user")
    public ResponseEntity<UserDto> addTestUser() {
        UserDto userDto = authService.addTestUser();
        return ResponseEntity.ok(userDto); // Devuelve el usuario guardado
    }*/
    @PostMapping({"/authenticate"})
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                        HttpServletResponse response) throws IOException, JSONException {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),authenticationRequest.getPassword()
            ));
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Nombre de usuario o clave incorrecto",e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwUtil.generateToken(userDetails.getUsername());

        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

        response.getWriter().write(new JSONObject()
                .put("userId", user.getId())
                .put("role",user.getRole())
                .toString()
        );

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization"+
                "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-Header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }

}
