package com.backend.games4u.controllers;

import com.backend.games4u.message.GameDeveloperOrPublisherPostDto;
import com.backend.games4u.message.GameDeveloperOrPublisherResponseDto;
import com.backend.games4u.message.PaginatedDeveloperOrPublisherResponseDto;
import com.backend.games4u.models.GameDeveloper;
import com.backend.games4u.models.GamePublisher;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.repository.GamePublisherRepository;
import com.backend.games4u.repository.VideoGameRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class GamePublisherController {

    @Autowired
    GamePublisherRepository gamePublishersRepository;

    @Autowired
    VideoGameRepository videoGameRepository;

    @GetMapping("/publishers")
    public ResponseEntity<List<GameDeveloperOrPublisherResponseDto>> getAllPublishers(@RequestParam(required = false) String publisherName) {

        try {
            List<GamePublisher> gamePublishers = new ArrayList<GamePublisher>();

            gamePublishersRepository.findAll().forEach(gamePublishers::add);

            if (publisherName != null) {
                gamePublishers =
                        gamePublishers.stream().filter(publisher -> publisher.getName().toLowerCase().contains(publisherName.toLowerCase())).collect(Collectors.toList());
            }

            if (gamePublishers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Optional<GamePublisher> nonePublisher = gamePublishersRepository.findByName("None");

            int noneIndex = gamePublishers.indexOf(nonePublisher.get());
            if (noneIndex != -1) {
                gamePublishers.remove(noneIndex);
                gamePublishers.add(0, nonePublisher.get());
            }

            List<GameDeveloperOrPublisherResponseDto> gameDeveloperResponseDtos = new ArrayList<>();

            for (GamePublisher gamePublisher : gamePublishers) {

                gameDeveloperResponseDtos.add(
                        new GameDeveloperOrPublisherResponseDto(
                                gamePublisher.getId(),
                                gamePublisher.getName(),
                                gamePublisher.getEstablishDate()
                        )
                );
            }

            return new ResponseEntity<>(gameDeveloperResponseDtos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/publishers_paginated")
    public ResponseEntity<PaginatedDeveloperOrPublisherResponseDto> getAllPublishersPaginated(
            @RequestParam(required = false) String publisherName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            List<GamePublisher> gamePublishers = new ArrayList<GamePublisher>();

            Pageable paging = PageRequest.of(page, size);
            Page<GamePublisher> pagePublishers;

            if (publisherName == null)
                pagePublishers = gamePublishersRepository.findAll(paging);
            else
                pagePublishers = gamePublishersRepository
                        .findGamePublisherByNameContainsIgnoreCase(publisherName, paging);

            gamePublishers = pagePublishers.getContent();

            if (gamePublishers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<GameDeveloperOrPublisherResponseDto> gamePublisherResponseDtos = new ArrayList<>();

            for (GamePublisher gamePublisher : gamePublishers) {

                gamePublisherResponseDtos.add(
                        new GameDeveloperOrPublisherResponseDto(
                                gamePublisher.getId(),
                                gamePublisher.getName(),
                                gamePublisher.getEstablishDate()
                        )
                );
            }

            PaginatedDeveloperOrPublisherResponseDto paginatedPublisherResponseDto
                    = new PaginatedDeveloperOrPublisherResponseDto(
                    pagePublishers.getTotalElements(),
                    pagePublishers.getTotalPages(),
                    pagePublishers.getNumber(),
                    gamePublisherResponseDtos);

            return new ResponseEntity<>(paginatedPublisherResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/publishers/{id}")
    public ResponseEntity<GameDeveloperOrPublisherResponseDto> getPublisherById(@PathVariable("id") long id) {

        Optional<GamePublisher> publisherData =
                Optional.ofNullable(gamePublishersRepository.findById(id));

        if (publisherData.isPresent()) {
            return new ResponseEntity<>(
                    new GameDeveloperOrPublisherResponseDto(
                            publisherData.get().getId(),
                            publisherData.get().getName(),
                            publisherData.get().getEstablishDate()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/publishers")
    public ResponseEntity<GamePublisher> createNewPublisher(@RequestBody GameDeveloperOrPublisherPostDto gameDeveloperPostDto) {

        try {
            DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

            Date establishDate =
                    DateUtils
                            .addDays(
                                    Date.from(LocalDate.parse(gameDeveloperPostDto.getEstablishDate(), dtfOutput)
                                            .atStartOfDay(ZoneId.of( "Asia/Kolkata" )).toInstant()), 1);

            GamePublisher newPublisher =
                    gamePublishersRepository.save(
                            new GamePublisher(
                                    gameDeveloperPostDto.getName(),
                                    establishDate
                            )
                    );

            return new ResponseEntity<>(newPublisher, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<HttpStatus> deletePublisherById(@PathVariable("id") long id) {

        try {
            Optional<GamePublisher> gamePublisher =
                    Optional.ofNullable(gamePublishersRepository.findById(id));
            Optional<GamePublisher> nonePublisher = gamePublishersRepository.findByName("None");

            if (gamePublisher.isPresent()) {

                List<VideoGame> videoGames = new ArrayList<VideoGame>();
                videoGameRepository.findAll().forEach(videoGames::add);

                videoGames =
                        videoGames.stream()
                                .filter(game -> game.getGamePublisher().getName().equals(gamePublisher.get().getName())).collect(Collectors.toList());

                if (videoGames.size() != 0) {
                    for (VideoGame videoGame : videoGames) {

                        videoGame.setGamePublisher(nonePublisher.get());
                        videoGameRepository.save(videoGame);
                    }
                }
            }

            gamePublishersRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/publishers")
    public ResponseEntity<HttpStatus> deleteAllPublishers() {

        try {
            gamePublishersRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
