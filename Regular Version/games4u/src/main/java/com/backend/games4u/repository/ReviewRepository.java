package com.backend.games4u.repository;

import com.backend.games4u.models.Review;
import com.backend.games4u.models.User;
import com.backend.games4u.models.VideoGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    void deleteAllByVideoGame(VideoGame videoGame);

    Page<Review> findReviewByVideoGame(VideoGame videoGame, Pageable pageable);

    Page<Review> findReviewByUser(User user, Pageable pageable);

    Optional<Review> findReviewByUserAndVideoGame(User user, VideoGame videoGame);
}
