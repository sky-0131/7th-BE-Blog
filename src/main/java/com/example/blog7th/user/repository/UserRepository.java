package com.example.blog7th.user.repository;

import com.example.blog7th.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
