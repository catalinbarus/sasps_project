package com.backend.games4u.repository;

import com.backend.games4u.models.Genre;
import com.backend.games4u.models.VideoGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {

    VideoGame findById(long id);

    Page<VideoGame> findVideoGameByNameContainsIgnoreCase(String name, Pageable pageable);

    Optional<List<VideoGame>> findAllByGenreAndIdNotIn(Genre genre, List<Long> ids, Pageable pageable);
}
