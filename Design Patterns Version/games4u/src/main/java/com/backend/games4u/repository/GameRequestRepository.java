package com.backend.games4u.repository;

import com.backend.games4u.models.GameRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRequestRepository extends JpaRepository<GameRequest, Long> {

    Page<GameRequest> findGameRequestByGameNameContainsIgnoreCase(String name, Pageable pageable);
}
