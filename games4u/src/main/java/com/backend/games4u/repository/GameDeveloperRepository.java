package com.backend.games4u.repository;

import com.backend.games4u.models.GameDeveloper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameDeveloperRepository extends JpaRepository<GameDeveloper, Long> {

    GameDeveloper findById (long id);

    Optional<GameDeveloper> findByName (String name);

    Page<GameDeveloper> findGameDeveloperByNameContainsIgnoreCase(String name, Pageable pageable);
}
