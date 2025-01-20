package com.backend.games4u.controllers;

import com.backend.games4u.message.*;
import com.backend.games4u.models.*;
import com.backend.games4u.payload.response.MessageResponse;
import com.backend.games4u.repository.*;
import com.backend.games4u.services.ImageResponseService;
import com.backend.games4u.services.VideoGameLightService;
import com.backend.games4u.services.VideoGameService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class VideoGameController {

    @Autowired
    VideoGameRepository videoGameRepository;

    @Autowired
    ImageResponseService imageResponseService;

    @Autowired
    GameDeveloperRepository gameDeveloperRepository;

    @Autowired
    GamePublisherRepository gamePublisherRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyGameListEntryRepository myGameListEntryRepository;

    @Autowired
    private VideoGameService videoGameService;

    @Autowired
    private VideoGameLightService videoGameLightService;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping
    public ResponseEntity<PaginatedVideoGameResponseDto> getAllGames(
            @RequestParam(required = false) String gameName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "averageScore,desc") String[] sort,
            @RequestParam(name = "user_id", required = false) Optional<Long> userId) {
        try {
            PaginatedVideoGameResponseDto response = videoGameService.getPaginatedGames(gameName, page, size, sort, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/games_light")
    public ResponseEntity<PaginatedVideoGameResponseLightDto> getAllGamesLight(
            @RequestParam(required = false) String gameName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "averageScore,desc") String[] sort,
            @RequestParam(name = "user_id", required = false) Optional<Long> userId) {

        try {
            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {

                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<VideoGame> videoGames = new ArrayList<VideoGame>();
            List<VideoGameResponseLightDto> videGamesResponse = new ArrayList<>();

            Pageable paging = PageRequest.of(page, size, Sort.by(orders).and(Sort.by("id")));
            Page<VideoGame> pageGames;

            if (gameName == null)
                pageGames = videoGameRepository.findAll(paging);
            else
                pageGames = videoGameRepository.findVideoGameByNameContainsIgnoreCase(gameName, paging);

            videoGames = pageGames.getContent();

            if (videoGames.isEmpty()) {
                PaginatedVideoGameResponseLightDto paginatedGameRequestResponseLightDto
                        = new PaginatedVideoGameResponseLightDto(
                        pageGames.getTotalElements(),
                        pageGames.getTotalPages(),
                        pageGames.getNumber(),
                        videGamesResponse);

                return new ResponseEntity<>(paginatedGameRequestResponseLightDto, HttpStatus.OK);
            }

            for (VideoGame game : videoGames) {

                ImageResponseDto boxart =
                        imageResponseService.getBoxartInTheProperResponse(game);

                Set<ScoreResponseDto> scoreResponseDtos = new HashSet<>();

                if (userId.isPresent()) {
                    scoreResponseDtos =
                            game.getScores()
                                    .stream()
                                    .filter(s -> s.getUser().getId() == userId.get())
                                    .flatMap(s -> Stream.of(new ScoreResponseDto(s.getId(),
                                            s.getScore(), s.getUser().getId(), s.getVideoGame().getId())))
                                    .collect(Collectors.toSet());
                } else {
                    scoreResponseDtos =
                            game.getScores()
                                    .stream()
                                    .flatMap(s -> Stream.of(new ScoreResponseDto(s.getId(),
                                            s.getScore(), s.getUser().getId(), s.getVideoGame().getId())))
                                    .collect(Collectors.toSet());
                }


                Set<ReviewResponseDto> reviewResponseDtos =
                        game.getReviews()
                                .stream()
                                .flatMap(r -> Stream.of(
                                        new ReviewResponseDto(
                                                r.getId(),
                                                r.getReview(),
                                                r.getUser().getId(),
                                                r.getVideoGame().getId(),
                                                r.getCreatedAt())))
                                .collect(Collectors.toSet());

                Set<TrailerResponseDto> trailerResponseDtos =
                        game.getTrailers()
                                .stream()
                                .flatMap(t -> Stream.of(
                                        new TrailerResponseDto(
                                                t.getId(),
                                                t.getYoutubeId(),
                                                t.getVideoGame().getId())))
                                .collect(Collectors.toSet());

                VideoGameResponseLightDto videoGameResponseDto =
                        new VideoGameResponseLightDto(
                                game.getId(),
                                game.getName(),
                                game.getGameDeveloper(),
                                game.getGamePublisher(),
                                game.getReleaseDate(),
                                game.getGenre(),
                                game.getRating(),
                                game.getDescription(),
                                boxart.getConvertedData() == null ? new ImageResponseDto() : boxart,
                                String.format("%.2f", game.getAverageScore()),
                                scoreResponseDtos,
                                reviewResponseDtos,
                                trailerResponseDtos);

                videGamesResponse.add(videoGameResponseDto);
            }

            if (videGamesResponse.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            PaginatedVideoGameResponseLightDto paginatedGameRequestResponseLightDto
                    = new PaginatedVideoGameResponseLightDto(
                    pageGames.getTotalElements(),
                    pageGames.getTotalPages(),
                    pageGames.getNumber(),
                    videGamesResponse);

            return new ResponseEntity<>(paginatedGameRequestResponseLightDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<VideoGameResponseDto> getGameById(@PathVariable("id") long id) {

        Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(id));

        if (videoGame.isPresent()) {

            List<ImageResponseDto> photos =
                    imageResponseService.getPhotosInTheProperResponse(videoGame.get());


            Set<ScoreResponseDto> scoreResponseDtos =
                    videoGame.get().getScores()
                            .stream()
                            .flatMap(s -> Stream.of(new ScoreResponseDto(s.getId(),s.getScore(),
                                    s.getUser().getId(), s.getVideoGame().getId())))
                            .collect(Collectors.toSet());

            Set<ReviewResponseDto> reviewResponseDtos =
                    videoGame.get().getReviews()
                            .stream()
                            .flatMap(r -> Stream.of(
                                    new ReviewResponseDto(
                                            r.getId(),
                                            r.getReview(),
                                            r.getUser().getId(),
                                            r.getVideoGame().getId(),
                                            r.getCreatedAt())))
                            .collect(Collectors.toSet());

            Set<TrailerResponseDto> trailerResponseDtos =
                    videoGame.get().getTrailers()
                            .stream()
                            .flatMap(t -> Stream.of(
                                    new TrailerResponseDto(
                                            t.getId(),
                                            t.getYoutubeId(),
                                            t.getVideoGame().getId())))
                            .collect(Collectors.toSet());

            VideoGameResponseDto videoGameResponseDto =
                    new VideoGameResponseDto(
                            videoGame.get().getId(),
                            videoGame.get().getName(),
                            videoGame.get().getGameDeveloper(),
                            videoGame.get().getGamePublisher(),
                            videoGame.get().getReleaseDate(),
                            videoGame.get().getGenre(),
                            videoGame.get().getRating(),
                            videoGame.get().getDescription(),
                            photos,
                            String.format("%.2f", videoGame.get().getAverageScore()),
                            scoreResponseDtos,
                            reviewResponseDtos,
                            trailerResponseDtos);

            return new ResponseEntity<>(videoGameResponseDto, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/games/recommendations/{id}")
    public ResponseEntity<List<VideoGameResponseDto>> getRecommendationsForUser(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Optional<User> user = userRepository.findById(id);
        List<VideoGame> recommendedGames = new ArrayList<>();
        List<VideoGameResponseDto> videGamesResponse = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(page, size);

        if (user.isPresent()) {
            Optional<List<MyGameListEntry>> myGameListEntries =
                    myGameListEntryRepository.findAllByUser(user.get());

            HashMap<String, Long> gamesByGenre = new HashMap<>();
            List<Long> gameIds = new ArrayList<>();

            for (MyGameListEntry entry : myGameListEntries.get()) {

                Optional<VideoGame> videoGame =
                        Optional.ofNullable(videoGameRepository.findById(entry.getVideoGame().getId()));

                String genreKey = videoGame.get().getGenre().getName();

                if (gamesByGenre.containsKey(genreKey)) {

                    Long currentEntries = gamesByGenre.get(genreKey);
                    gamesByGenre.put(genreKey, currentEntries + 1);
                } else {
                    gamesByGenre.put(genreKey, 1L);
                }

                gameIds.add(videoGame.get().getId());
            }

            Map.Entry<String, Long> entryWithMaxValue = null;
            for (Map.Entry<String, Long> entry : gamesByGenre.entrySet()) {
                if (entryWithMaxValue == null || entry.getValue() > entryWithMaxValue.getValue()) {
                    entryWithMaxValue = entry;
                }
            }

            Optional<Genre> genre = genreRepository.findByName(entryWithMaxValue.getKey());

            Optional<List<VideoGame>> potentialRecommendations
                    = videoGameRepository.findAllByGenreAndIdNotIn(genre.get(), gameIds, pageRequest);


            for (VideoGame game : potentialRecommendations.get()) {
                List<ImageResponseDto> photos =
                        imageResponseService.getPhotosInTheProperResponse(game);

                Set<ScoreResponseDto> scoreResponseDtos = new HashSet<>();

                scoreResponseDtos =
                        game.getScores()
                                .stream()
                                .flatMap(s -> Stream.of(new ScoreResponseDto(s.getId(),
                                        s.getScore(), s.getUser().getId(), s.getVideoGame().getId())))
                                .collect(Collectors.toSet());

                Set<ReviewResponseDto> reviewResponseDtos =
                        game.getReviews()
                                .stream()
                                .flatMap(r -> Stream.of(
                                        new ReviewResponseDto(
                                                r.getId(),
                                                r.getReview(),
                                                r.getUser().getId(),
                                                r.getVideoGame().getId(),
                                                r.getCreatedAt())))
                                .collect(Collectors.toSet());

                Set<TrailerResponseDto> trailerResponseDtos =
                        game.getTrailers()
                                .stream()
                                .flatMap(t -> Stream.of(
                                        new TrailerResponseDto(
                                                t.getId(),
                                                t.getYoutubeId(),
                                                t.getVideoGame().getId())))
                                .collect(Collectors.toSet());

                VideoGameResponseDto videoGameResponseDto =
                        new VideoGameResponseDto(
                                game.getId(),
                                game.getName(),
                                game.getGameDeveloper(),
                                game.getGamePublisher(),
                                game.getReleaseDate(),
                                game.getGenre(),
                                game.getRating(),
                                game.getDescription(),
                                photos,
                                String.format("%.2f", game.getAverageScore()),
                                scoreResponseDtos,
                                reviewResponseDtos,
                                trailerResponseDtos);

                videGamesResponse.add(videoGameResponseDto);
            }
        }

        return new ResponseEntity<>(videGamesResponse, HttpStatus.OK);
    }

    @PostMapping("/games")
    public ResponseEntity<VideoGame> createNewVideoGame(@RequestBody VideoGamePostDto videoGamePostDto) {

        try {
            Optional<GameDeveloper> gameDeveloper =
                    Optional.ofNullable(gameDeveloperRepository.findById(videoGamePostDto.getDeveloperId()));

            Optional<GamePublisher> gamePublisher =
                    Optional.ofNullable(gamePublisherRepository.findById(videoGamePostDto.getPublisherId()));

            Optional<Genre> genre = Optional.ofNullable(genreRepository.findById(videoGamePostDto.getGenreId()));

            if (gamePublisher.isPresent() && gameDeveloper.isPresent() && genre.isPresent()) {

                DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

                Date releaseDate =
                        DateUtils
                                .addDays(
                                        Date.from(LocalDate.parse(videoGamePostDto.getReleaseDate(), dtfOutput)
                                                .atStartOfDay(ZoneId.of( "Asia/Kolkata" )).toInstant()), 1);


                Set<ImageDB> images = new HashSet<>();

                Set<Score> scores = new HashSet<>();

                Set<Review> reviews = new HashSet<>();

                Set<Trailer> trailers = new HashSet<>();

                VideoGame videoGame = videoGameRepository.save(
                        new VideoGame(
                                videoGamePostDto.getName(),
                                gameDeveloper.get(),
                                gamePublisher.get(),
                                releaseDate,
                                genre.get(),
                                videoGamePostDto.getRating(),
                                videoGamePostDto.getDescription(),
                                0.0,
                                images,
                                trailers,
                                scores,
                                reviews
                        )
                );

                return new ResponseEntity<>(videoGame, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<?> updateGame(@PathVariable("id") long gameId,
                                               @RequestBody VideoGamePostDto videoGamePostDto) {

        Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(gameId));

        if (videoGame.isPresent()) {

            Optional<GameDeveloper> gameDeveloper =
                    Optional.ofNullable(gameDeveloperRepository.findById(videoGamePostDto.getDeveloperId()));

            Optional<GamePublisher> gamePublisher =
                    Optional.ofNullable(gamePublisherRepository.findById(videoGamePostDto.getPublisherId()));

            Optional<Genre> genre = Optional.ofNullable(genreRepository.findById(videoGamePostDto.getGenreId()));

            DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

            Date releaseDate =
                    DateUtils
                            .addDays(
                                    Date.from(LocalDate.parse(videoGamePostDto.getReleaseDate(), dtfOutput)
                                            .atStartOfDay(ZoneId.of( "Asia/Kolkata" )).toInstant()), 1);

            videoGame.get().setName(videoGamePostDto.getName());
            videoGame.get().setGameDeveloper(gameDeveloper.get());
            videoGame.get().setGamePublisher(gamePublisher.get());
            videoGame.get().setReleaseDate(releaseDate);
            videoGame.get().setGenre(genre.get());
            videoGame.get().setRating(videoGamePostDto.getRating());
            videoGame.get().setDescription(videoGamePostDto.getDescription());

            videoGameRepository.save(videoGame.get());

            return ResponseEntity.ok(new MessageResponse("Game updated successfully"));

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<HttpStatus> deleteGameById(@PathVariable("id") long id) {

        try {
            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(id));

            if (videoGame.isPresent()) {

                videoGameRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
