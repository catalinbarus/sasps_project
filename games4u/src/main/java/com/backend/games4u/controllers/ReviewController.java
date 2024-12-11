package com.backend.games4u.controllers;

import com.backend.games4u.message.*;
import com.backend.games4u.models.Review;
import com.backend.games4u.models.Score;
import com.backend.games4u.models.User;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.repository.ReviewRepository;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class ReviewController {

    @Autowired
    VideoGameRepository videoGameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {

        try {

            List<Review> reviews = new ArrayList<Review>();

            reviewRepository.findAll().forEach(reviews::add);

            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();

            for (Review review : reviews) {

                reviewResponseDtos.add(
                        new ReviewResponseDto(
                                review.getId(),
                                review.getReview(),
                                review.getUser().getId(),
                                review.getVideoGame().getId(),
                                review.getCreatedAt()
                        )
                );
            }

            return new ResponseEntity<>(reviewResponseDtos, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reviews/games/{id}")
    public ResponseEntity<PaginatedReviewResponseDto> getAllReviewsOfAGame(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(id));

            List<Review> reviews = new ArrayList<Review>();
            List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();

            Pageable paging = PageRequest.of(page, size, Sort.by(orders));
            Page<Review> pageReviews;

            pageReviews = reviewRepository.findReviewByVideoGame(videoGame.get(), paging);

            reviews = pageReviews.getContent();

            if (reviews.isEmpty()) {
                PaginatedReviewResponseDto paginatedReviewResponseDto
                        = new PaginatedReviewResponseDto(
                        pageReviews.getTotalElements(),
                        pageReviews.getTotalPages(),
                        pageReviews.getNumber(),
                        reviewResponseDtos);

                return new ResponseEntity<>(paginatedReviewResponseDto, HttpStatus.OK);
            }

            for (Review review : reviews) {

                reviewResponseDtos.add(
                        new ReviewResponseDto(
                                review.getId(),
                                review.getReview(),
                                review.getUser().getId(),
                                review.getVideoGame().getId(),
                                review.getCreatedAt()
                        )
                );
            }

            PaginatedReviewResponseDto paginatedReviewResponseDto
                    = new PaginatedReviewResponseDto(
                    pageReviews.getTotalElements(),
                    pageReviews.getTotalPages(),
                    pageReviews.getNumber(),
                    reviewResponseDtos);

            return new ResponseEntity<>(paginatedReviewResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reviews/users/{id}")
    public ResponseEntity<PaginatedReviewResponseDto> getRecentReviewsOfUser(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            Optional<User> user = userRepository.findById(id);

            List<Review> reviews = new ArrayList<Review>();
            List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();

            Pageable paging = PageRequest.of(page, size, Sort.by(orders));
            Page<Review> pageReviews;

            pageReviews = reviewRepository.findReviewByUser(user.get(), paging);

            reviews = pageReviews.getContent();

            if (reviews.isEmpty()) {
                PaginatedReviewResponseDto paginatedReviewResponseDto
                        = new PaginatedReviewResponseDto(
                        pageReviews.getTotalElements(),
                        pageReviews.getTotalPages(),
                        pageReviews.getNumber(),
                        reviewResponseDtos);

                return new ResponseEntity<>(paginatedReviewResponseDto, HttpStatus.OK);
            }

            for (Review review : reviews) {

                reviewResponseDtos.add(
                        new ReviewResponseDto(
                                review.getId(),
                                review.getReview(),
                                review.getUser().getId(),
                                review.getVideoGame().getId(),
                                review.getCreatedAt()
                        )
                );
            }

            PaginatedReviewResponseDto paginatedReviewResponseDto
                    = new PaginatedReviewResponseDto(
                    pageReviews.getTotalElements(),
                    pageReviews.getTotalPages(),
                    pageReviews.getNumber(),
                    reviewResponseDtos);

            return new ResponseEntity<>(paginatedReviewResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable("id") long id) {

        try {
            Optional<Review> review = reviewRepository.findById(id);

            if (review.isPresent()) {

                ReviewResponseDto reviewResponseDto =
                        new ReviewResponseDto(
                                review.get().getId(),
                                review.get().getReview(),
                                review.get().getUser().getId(),
                                review.get().getVideoGame().getId(),
                                review.get().getCreatedAt()
                        );

                return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reviews/filter")
    public ResponseEntity<ReviewResponseDto> getReviewOfAUser(
            @RequestParam("user_id") long userId,
            @RequestParam("game_id") long gameId) {

        try {
            Optional<User> user = userRepository.findById(userId);
            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(gameId));

            if (user.isPresent() && videoGame.isPresent()) {

                Optional<Review> review =
                        reviewRepository.findReviewByUserAndVideoGame(user.get(), videoGame.get());

                if (review.isPresent()) {
                    ReviewResponseDto reviewResponseDto =
                            new ReviewResponseDto(
                                    review.get().getId(),
                                    review.get().getReview(),
                                    review.get().getUser().getId(),
                                    review.get().getVideoGame().getId(),
                                    review.get().getCreatedAt()
                            );

                    return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
                }

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> createNewReview(@RequestBody ReviewPostDto review) {

        try {
            Optional<VideoGame> videoGame =
                    Optional.ofNullable(videoGameRepository.findById(Long.parseLong(review.getGameId())));

            Optional<User> user = userRepository.findById(Long.parseLong(review.getUserId()));

            if (videoGame.isPresent() && user.isPresent()) {

                Review newReview =
                        reviewRepository.save(
                                new Review(
                                        review.getReview(),
                                        videoGame.get(),
                                        user.get(),
                                        new Date()
                                )
                        );

                return new ResponseEntity<>(newReview, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") long reviewId,
                                             @RequestBody ReviewPutDto reviewPutDto) {

        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {

            review.get().setReview(reviewPutDto.getReview());

            return new ResponseEntity<>(reviewRepository.save(review.get()), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<HttpStatus> deleteReviewById(@PathVariable("id") long id) {

        try {
            Optional<Review> review = reviewRepository.findById(id);

            if (review.isPresent()) {
                reviewRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
