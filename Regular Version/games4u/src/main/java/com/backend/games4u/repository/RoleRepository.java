package com.backend.games4u.repository;

import com.backend.games4u.models.ERole;
import com.backend.games4u.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
