package uz.kholmakhmatov.lesson_03_spring_security_2_jwt.secuirty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.service.MyAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MyAuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            boolean validateToken = jwtProvider.validateToken(token);
            if (validateToken) {
                String username = jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails = myAuthService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            }

        }

//        filterChain.doFilter(httpServletRequest, httpServletResponse);
        filterChain.doFilter(httpServletRequest,httpServletResponse);// Keladigan responsni har bir kelgan zaprosdan song tekshirish

    }
}
