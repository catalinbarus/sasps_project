package com.backend.games4u.services.review;

import com.backend.games4u.message.*;
import com.backend.games4u.models.Review;
import com.backend.games4u.models.User;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.repository.ReviewRepository;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        try {
            List<Review> reviews = reviewRepository.findAll();
            if (reviews.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return ResponseEntity.ok(ReviewMapper.toDtoList(reviews));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<PaginatedReviewResponseDto> getReviewsByGame(long gameId, int page, int size, String[] sort) {
        return getPaginatedReviews(
                Optional.ofNullable(videoGameRepository.findById(gameId)),
                Optional.empty(),
                page,
                size,
                sort
        );
    }

    public ResponseEntity<PaginatedReviewResponseDto> getReviewsByUser(long userId, int page, int size, String[] sort) {
        return getPaginatedReviews(
                Optional.empty(),
                userRepository.findById(userId),
                page,
                size,
                sort
        );
    }

    public ResponseEntity<ReviewResponseDto> getReviewById(long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.map(value -> ResponseEntity.ok(ReviewMapper.toDto(value)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Review> createReview(ReviewPostDto reviewPostDto) {
        try {
            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(Long.parseLong(reviewPostDto.getGameId())));
            Optional<User> user = userRepository.findById(Long.parseLong(reviewPostDto.getUserId()));
            if (videoGame.isPresent() && user.isPresent()) {
                Review review = new Review(reviewPostDto.getReview(), videoGame.get(), user.get(), new Date());
                return new ResponseEntity<>(reviewRepository.save(review), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Review> updateReview(long reviewId, ReviewPutDto reviewPutDto) {
        return reviewRepository.findById(reviewId)
                .map(review -> {
                    review.setReview(reviewPutDto.getReview());
                    return ResponseEntity.ok(reviewRepository.save(review));
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<HttpStatus> deleteReviewById(long reviewId) {
        try {
            if (reviewRepository.existsById(reviewId)) {
                reviewRepository.deleteById(reviewId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<PaginatedReviewResponseDto> getPaginatedReviews(
            Optional<VideoGame> videoGame, Optional<User> user, int page, int size, String[] sort) {
        try {
            List<Sort.Order> orders = parseSortParams(sort);
            Pageable paging = PageRequest.of(page, size, Sort.by(orders));
            Page<Review> pageReviews = videoGame.isPresent() ?
                    reviewRepository.findReviewByVideoGame(videoGame.get(), paging) :
                    reviewRepository.findReviewByUser(user.get(), paging);

            if (pageReviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(ReviewMapper.toPaginatedDto(pageReviews));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Sort.Order> parseSortParams(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParam : sort) {
            String[] parts = sortParam.split(",");
            orders.add(new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]));
        }
        return orders;
    }
}
