package uz.kholmakhmatov.lesson_03_spring_security_2_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.payload.LoginDto;
import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.secuirty.JwtProvider;
import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.service.MyAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    MyAuthService myAuthService;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public HttpEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            String token = jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(401).body("login yoki password xato");
        }
    }

}
