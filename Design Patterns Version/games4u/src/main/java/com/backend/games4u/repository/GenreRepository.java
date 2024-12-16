package com.backend.games4u.repository;

import com.backend.games4u.models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findById (long id);
    Optional<Genre> findByName (String name);

    Page<Genre> findGenreByNameContainsIgnoreCase(String name, Pageable pageable);
}
