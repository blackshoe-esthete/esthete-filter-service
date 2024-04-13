package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Optional<User> findByUserId(UUID userId);
}
