package uz.kholmakhmatov.lesson_03_spring_security_2_jwt.secuirty;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    static long expireTime = 36_000_0000;
    static String kalitSuz = "Anvar_3700@";

    public   String generateToken(String username) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, kalitSuz)
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(kalitSuz)
                    .parseClaimsJws(token);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//
    public String getUserNameFromToken(String token) {
        String username = Jwts
                .parser()
                .setSigningKey(kalitSuz)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }

//    public static void main(String[] args) {
//        String token = generateToken("pdp");
//        System.out.println(token);
//
//    }
}
