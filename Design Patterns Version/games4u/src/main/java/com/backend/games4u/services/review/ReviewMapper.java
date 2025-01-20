package com.backend.games4u.services.review;

import com.backend.games4u.message.PaginatedReviewResponseDto;
import com.backend.games4u.message.ReviewResponseDto;
import com.backend.games4u.models.Review;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static ReviewResponseDto toDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getReview(),
                review.getUser().getId(),
                review.getVideoGame().getId(),
                review.getCreatedAt()
        );
    }

    public static List<ReviewResponseDto> toDtoList(List<Review> reviews) {
        return reviews.stream().map(ReviewMapper::toDto).collect(Collectors.toList());
    }

    public static PaginatedReviewResponseDto toPaginatedDto(Page<Review> pageReviews) {
        List<ReviewResponseDto> reviews = toDtoList(pageReviews.getContent());
        return new PaginatedReviewResponseDto(
                pageReviews.getTotalElements(),
                pageReviews.getTotalPages(),
                pageReviews.getNumber(),
                reviews
        );
    }
}
