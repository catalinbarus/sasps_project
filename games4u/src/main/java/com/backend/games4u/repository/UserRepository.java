package com.backend.games4u.repository;

import com.backend.games4u.models.User;
import com.backend.games4u.models.UserImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Page<User> findUserByUsernameContainsIgnoreCase(String name, Pageable pageable);

    Optional<User> findUserByUserImage(UserImage userImage);
}
