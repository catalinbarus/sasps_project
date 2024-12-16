package com.backend.games4u.repository;

import com.backend.games4u.models.ImageDB;
import com.backend.games4u.models.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageDBRepository extends JpaRepository<ImageDB, Long> {
    void deleteAllByVideoGame(VideoGame videoGame);

    Optional<List<ImageDB>> findAllByVideoGame(VideoGame videoGame);

    Optional<ImageDB> findByVideoGameAndNameStartsWith(VideoGame videoGame, String startString);
}
