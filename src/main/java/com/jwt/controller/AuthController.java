package com.jwt.controller;

import com.jwt.dto.LoginRequest;
import com.jwt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 사용자 인증 후 JWT 토큰 생성
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (token == null) {
            // 로그인 실패: 인증 실패시 처리
            return ResponseEntity.status(401).body("로그인 실패: 잘못된 자격 증명");
        }
        // 로그인 성공: JWT 토큰 반환
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        authService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("사용자가 성공적으로 등록되었습니다");
    }

    @GetMapping("/authpost")
    public ResponseEntity<String> authpost() {
        return ResponseEntity.ok("인증된 사용자입니다.");
    }
}