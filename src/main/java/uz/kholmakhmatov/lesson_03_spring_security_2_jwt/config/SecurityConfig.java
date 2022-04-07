package uz.kholmakhmatov.lesson_03_spring_security_2_jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.secuirty.JwtFilter;
import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.secuirty.JwtFilter;
import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.service.MyAuthService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyAuthService myAuthService;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(myAuthService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// web ilovada boshqa  ilovalarga ham ruxsatni yoq
                .cors().disable() // database ga malumot qosh va undagi malumotlarni ozgartir
                .authorizeRequests()
                .antMatchers("/", "/api/auth/login") // bu yollrga security tegmasin
                .permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);// tizimga kirshdan oldin jwtFilter dan otkaz
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // har safar qayta qayta jwt tokenni tekshir
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
