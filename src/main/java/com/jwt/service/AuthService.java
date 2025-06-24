package com.jwt.service;

import com.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.jwt.repository.UserRepository;
import com.jwt.entity.User;

@Service
public class AuthService {

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // JwtUtil 추가

    // 생성자를 통한 의존성 주입
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 로그인 처리: 사용자명과 비밀번호로 인증 후 JWT 토큰 생성
    public String login(String username, String password) {
        // 1. 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // 3. JWT 토큰 생성 및 반환
        return jwtUtil.generateToken(username, user.getUsername(), EXPIRATION_TIME);
    }

    public void register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String encoded = passwordEncoder.encode(password);
        userRepository.save(new User(username, encoded));
    }
}
