package com.backend.games4u.controllers;

import com.backend.games4u.message.ScorePostDto;
import com.backend.games4u.message.ScorePutDto;
import com.backend.games4u.message.ScoreResponseDto;
import com.backend.games4u.models.*;
import com.backend.games4u.repository.MyGameListEntryRepository;
import com.backend.games4u.repository.ScoreRepository;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class ScoreController {

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    VideoGameRepository videoGameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyGameListEntryRepository myGameListEntryRepository;

    @GetMapping("/scores")
    public ResponseEntity<List<ScoreResponseDto>> getAllScores() {

        try {

            List<Score> scores = new ArrayList<Score>();

            scoreRepository.findAll().forEach(scores::add);

            if (scores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<ScoreResponseDto> scoreResponseDtos = new ArrayList<>();

            for (Score score : scores) {

                scoreResponseDtos.add(
                        new ScoreResponseDto(
                                score.getId(),
                                score.getScore(),
                                score.getUser().getId(),
                                score.getVideoGame().getId()
                        )
                );
            }

            return new ResponseEntity<>(scoreResponseDtos, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/scores/recent_scores/{id}")
    public ResponseEntity<List<ScoreResponseDto>> getRecentScoresForUser(@PathVariable("id") long id) {

        try {

            Optional<User> user = userRepository.findById(id);
            List<Score> scores = new ArrayList<Score>();

            if (user.isPresent()) {
                Optional<List<Score>> potentialScores = scoreRepository.findAllByUser(user.get());

                if (potentialScores.isPresent()) {
                    scores = potentialScores.get();
                }
            }

            if (scores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Collections.sort(scores, Comparator.comparingLong(Score::getId).reversed());

            List<ScoreResponseDto> scoreResponseDtos = new ArrayList<>();

            int count = 0;

            for (Score score : scores) {

                if (score != null) {
                    count++;
                } else {
                    break;
                }

                scoreResponseDtos.add(
                        new ScoreResponseDto(
                                score.getId(),
                                score.getScore(),
                                score.getUser().getId(),
                                score.getVideoGame().getId()
                        )
                );

                if (count == 3) {
                    break;
                }
            }

            return new ResponseEntity<>(scoreResponseDtos, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/scores/mean/{id}")
    public ResponseEntity<String> getMeanScoreForUser(@PathVariable("id") long id) {

        try {

            Optional<User> user = userRepository.findById(id);
            String mean = "";
            int total = 0;
            long sum = 0;

            if (user.isPresent()) {

                Optional<List<Score>> scores = scoreRepository.findAllByUser(user.get());

                if (scores.isPresent()) {

                    for (Score score : scores.get()) {
                        sum += score.getScore();
                        total ++;
                    }

                    Double meanScore = (double) sum / total;
                    mean = meanScore.toString();
                } else {
                    mean = "0.00";
                }
            } else {
                mean = "0.00";
            }

            return new ResponseEntity<>(mean, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/scores/{id}")
    public ResponseEntity<ScoreResponseDto> getScoreById(@PathVariable("id") long id) {

        try {
            Optional<Score> score = scoreRepository.findById(id);

            if (score.isPresent()) {

                ScoreResponseDto scoreResponseDto =
                        new ScoreResponseDto(
                                score.get().getId(),
                                score.get().getScore(),
                                score.get().getUser().getId(),
                                score.get().getVideoGame().getId()
                        );

                return new ResponseEntity<>(scoreResponseDto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/scores/filter")
    public ResponseEntity<ScoreResponseDto> getScoreByUserAndGame(@RequestParam("user_id") long userId, @RequestParam("game_id") long gameId) {

        try {

            Optional<User> user = userRepository.findById(userId);

            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(gameId));

            if (user.isPresent() && videoGame.isPresent()) {

                Optional<Score> score = scoreRepository.findByUserAndVideoGame(user.get(), videoGame.get());

                if (score.isPresent()) {

                    ScoreResponseDto scoreResponseDto =
                            new ScoreResponseDto(
                                    score.get().getId(),
                                    score.get().getScore(),
                                    score.get().getUser().getId(),
                                    score.get().getVideoGame().getId()
                            );

                    return new ResponseEntity<>(scoreResponseDto, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/scores")
    public ResponseEntity<Score> createNewScore(@RequestBody ScorePostDto score) {

        try {
            Optional<VideoGame> videoGame =
                    Optional.ofNullable(videoGameRepository.findById(Long.parseLong(score.getGameId())));

            Optional<User> user = userRepository.findById(Long.parseLong(score.getUserId()));

            if (videoGame.isPresent() && user.isPresent()) {

                Score newScore =
                        scoreRepository.save(
                                new Score(
                                        Long.parseLong(score.getScore()),
                                        videoGame.get(),
                                        user.get()
                                )
                        );

                Set<Score> currentGameScoresSet = videoGame.get().getScores();

                List<Integer> currentGameScores =
                        currentGameScoresSet
                                .stream()
                                .flatMap(s -> Stream.of(s.getScore()))
                                .map(Long::intValue)
                                .collect(Collectors.toList());

                Double averageScore =
                        (currentGameScores.stream().reduce(0, (x, y) -> x + y) / (Double.valueOf(currentGameScores.size())));

                videoGame.get().setAverageScore(averageScore);
                videoGameRepository.save(videoGame.get());


                return new ResponseEntity<>(newScore, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/scores/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable("id") long scoreId,
                                             @RequestBody ScorePutDto scorePutDto) {

        Optional<Score> score = scoreRepository.findById(scoreId);
        Optional<VideoGame> videoGame
                = Optional.ofNullable(videoGameRepository.findById(scorePutDto.getGameId()));

        if (score.isPresent()) {

            score.get().setScore(Long.parseLong(scorePutDto.getScore()));

            Set<Score> currentGameScoresSet = videoGame.get().getScores();

            List<Integer> currentGameScores =
                    currentGameScoresSet
                            .stream()
                            .flatMap(s -> Stream.of(s.getScore()))
                            .map(Long::intValue)
                            .collect(Collectors.toList());

            Double averageScore =
                    (currentGameScores.stream().reduce(0, (x, y) -> x + y) / (Double.valueOf(currentGameScores.size())));

            videoGame.get().setAverageScore(averageScore);
            videoGameRepository.save(videoGame.get());

            return new ResponseEntity<>(scoreRepository.save(score.get()), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
