package com.backend.games4u.repository;

import com.backend.games4u.models.GamePublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GamePublisherRepository extends JpaRepository<GamePublisher, Long> {

    GamePublisher findById (long id);

    Optional<GamePublisher> findByName(String name);

    Page<GamePublisher> findGamePublisherByNameContainsIgnoreCase(String name, Pageable pageable);
}
