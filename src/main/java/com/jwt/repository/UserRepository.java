package com.jwt.repository;

import com.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  // 사용자명을 통한 사용자 조회
}
