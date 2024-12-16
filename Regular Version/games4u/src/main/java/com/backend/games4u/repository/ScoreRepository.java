package com.backend.games4u.repository;

import com.backend.games4u.models.Score;
import com.backend.games4u.models.User;
import com.backend.games4u.models.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findByUserAndVideoGame(User user, VideoGame videoGame);

    void deleteAllByVideoGame(VideoGame videoGame);

    Optional<List<Score>> findAllByUser(User user);
}
