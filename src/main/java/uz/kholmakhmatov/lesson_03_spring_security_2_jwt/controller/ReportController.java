package uz.kholmakhmatov.lesson_03_spring_security_2_jwt.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kholmakhmatov.lesson_03_spring_security_2_jwt.payload.LoginDto;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @GetMapping
    public HttpEntity<?> getReports() {
        return ResponseEntity.ok("Report sent");
    }

    @PostMapping
    public HttpEntity addTest(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(loginDto);
    }
}
