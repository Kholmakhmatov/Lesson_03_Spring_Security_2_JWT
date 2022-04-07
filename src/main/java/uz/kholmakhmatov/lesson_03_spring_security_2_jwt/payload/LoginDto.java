package uz.kholmakhmatov.lesson_03_spring_security_2_jwt.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
