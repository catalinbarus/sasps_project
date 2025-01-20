package com.backend.games4u.services;

import com.backend.games4u.message.*;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VideoGameService {

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private ImageResponseService imageResponseService;

    public PaginatedVideoGameResponseDto getPaginatedGames(String gameName, int page, int size, String[] sort, Optional<Long> userId) {
        Pageable paging = createPageable(sort, page, size);
        Page<VideoGame> pageGames = (gameName == null) ?
                videoGameRepository.findAll(paging) :
                videoGameRepository.findVideoGameByNameContainsIgnoreCase(gameName, paging);

        List<VideoGameResponseDto> gameResponses = pageGames.getContent().stream()
                .map(game -> buildVideoGameResponseDto(game, userId))
                .collect(Collectors.toList());

        return new PaginatedVideoGameResponseDto(
                pageGames.getTotalElements(),
                pageGames.getTotalPages(),
                pageGames.getNumber(),
                gameResponses);
    }

    private Pageable createPageable(String[] sort, int page, int size) {
        List<Sort.Order> orders = Arrays.stream(sort)
                .map(s -> s.split(","))
                .map(parts -> new Sort.Order(getSortDirection(parts[1]), parts[0]))
                .collect(Collectors.toList());
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        return PageRequest.of(page, size, Sort.by(orders));
    }

    private Sort.Direction getSortDirection(String direction) {
        return "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    private VideoGameResponseDto buildVideoGameResponseDto(VideoGame game, Optional<Long> userId) {
        List<ImageResponseDto> photos = imageResponseService.getPhotosInTheProperResponse(game);

        Set<ScoreResponseDto> scores = game.getScores().stream()
                .filter(score -> userId.map(id -> score.getUser().getId().equals(id)).orElse(true))
                .map(score -> new ScoreResponseDto(
                        score.getId(), score.getScore(), score.getUser().getId(), score.getVideoGame().getId()))
                .collect(Collectors.toSet());

        Set<ReviewResponseDto> reviews = game.getReviews().stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(), review.getReview(), review.getUser().getId(),
                        review.getVideoGame().getId(), review.getCreatedAt()))
                .collect(Collectors.toSet());

        Set<TrailerResponseDto> trailers = game.getTrailers().stream()
                .map(trailer -> new TrailerResponseDto(
                        trailer.getId(), trailer.getYoutubeId(), trailer.getVideoGame().getId()))
                .collect(Collectors.toSet());

        return new VideoGameResponseDto(
                game.getId(), game.getName(), game.getGameDeveloper(),
                game.getGamePublisher(), game.getReleaseDate(), game.getGenre(),
                game.getRating(), game.getDescription(), photos,
                String.format("%.2f", game.getAverageScore()), scores, reviews, trailers);
    }
}
