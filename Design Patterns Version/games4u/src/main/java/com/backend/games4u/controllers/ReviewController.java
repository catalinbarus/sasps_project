package com.backend.games4u.controllers;

import com.backend.games4u.message.*;
import com.backend.games4u.models.Review;
import com.backend.games4u.services.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/reviews/games/{id}")
    public ResponseEntity<PaginatedReviewResponseDto> getAllReviewsOfAGame(
            @PathVariable("id") long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        return reviewService.getReviewsByGame(gameId, page, size, sort);
    }

    @GetMapping("/reviews/users/{id}")
    public ResponseEntity<PaginatedReviewResponseDto> getRecentReviewsOfUser(
            @PathVariable("id") long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        return reviewService.getReviewsByUser(userId, page, size, sort);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable("id") long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> createNewReview(@RequestBody ReviewPostDto reviewPostDto) {
        return reviewService.createReview(reviewPostDto);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable("id") long reviewId,
            @RequestBody ReviewPutDto reviewPutDto) {
        return reviewService.updateReview(reviewId, reviewPutDto);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<HttpStatus> deleteReviewById(@PathVariable("id") long reviewId) {
        return reviewService.deleteReviewById(reviewId);
    }
}
