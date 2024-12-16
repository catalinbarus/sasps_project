package com.backend.games4u.controllers;

import com.backend.games4u.message.GameDeveloperOrPublisherPostDto;
import com.backend.games4u.message.GameDeveloperOrPublisherResponseDto;
import com.backend.games4u.message.PaginatedDeveloperOrPublisherResponseDto;
import com.backend.games4u.message.PaginatedGenreResponseDto;
import com.backend.games4u.models.GameDeveloper;
import com.backend.games4u.models.Genre;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.repository.GameDeveloperRepository;
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
public class GameDeveloperController {

    @Autowired
    GameDeveloperRepository gameDeveloperRepository;

    @Autowired
    VideoGameRepository videoGameRepository;

    @GetMapping("/developers")
    public ResponseEntity<List<GameDeveloperOrPublisherResponseDto>> getAllDevelopers(@RequestParam(required = false) String developerName) {

        try {
            List<GameDeveloper> gameDevelopers = new ArrayList<GameDeveloper>();

            gameDeveloperRepository.findAll().forEach(gameDevelopers::add);

            if (developerName != null) {
                gameDevelopers =
                        gameDevelopers.stream().filter(developer -> developer.getName().toLowerCase().contains(developerName.toLowerCase())).collect(Collectors.toList());
            }

            if (gameDevelopers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Optional<GameDeveloper> noneDeveloper = gameDeveloperRepository.findByName("None");

            int noneIndex = gameDevelopers.indexOf(noneDeveloper.get());
            if (noneIndex != -1) {
                gameDevelopers.remove(noneIndex);
                gameDevelopers.add(0, noneDeveloper.get());
            }

            List<GameDeveloperOrPublisherResponseDto> gameDeveloperResponseDtos = new ArrayList<>();

            for (GameDeveloper gameDeveloper : gameDevelopers) {

                gameDeveloperResponseDtos.add(
                        new GameDeveloperOrPublisherResponseDto(
                                gameDeveloper.getId(),
                                gameDeveloper.getName(),
                                gameDeveloper.getEstablishDate()
                        )
                );
            }

            return new ResponseEntity<>(gameDeveloperResponseDtos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/developers_paginated")
    public ResponseEntity<PaginatedDeveloperOrPublisherResponseDto> getAllDevelopersPaginated(
            @RequestParam(required = false) String developerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            List<GameDeveloper> gameDevelopers = new ArrayList<GameDeveloper>();

            Pageable paging = PageRequest.of(page, size);
            Page<GameDeveloper> pageDevelopers;

            if (developerName == null)
                pageDevelopers = gameDeveloperRepository.findAll(paging);
            else
                pageDevelopers = gameDeveloperRepository
                        .findGameDeveloperByNameContainsIgnoreCase(developerName, paging);

            gameDevelopers = pageDevelopers.getContent();

            if (gameDevelopers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<GameDeveloperOrPublisherResponseDto> gameDeveloperResponseDtos = new ArrayList<>();

            for (GameDeveloper gameDeveloper : gameDevelopers) {

                gameDeveloperResponseDtos.add(
                        new GameDeveloperOrPublisherResponseDto(
                                gameDeveloper.getId(),
                                gameDeveloper.getName(),
                                gameDeveloper.getEstablishDate()
                        )
                );
            }

            PaginatedDeveloperOrPublisherResponseDto paginatedDeveloperResponseDto
                    = new PaginatedDeveloperOrPublisherResponseDto(
                    pageDevelopers.getTotalElements(),
                    pageDevelopers.getTotalPages(),
                    pageDevelopers.getNumber(),
                    gameDeveloperResponseDtos);

            return new ResponseEntity<>(paginatedDeveloperResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/developers/{id}")
    public ResponseEntity<GameDeveloperOrPublisherResponseDto> getDeveloperById(@PathVariable("id") long id) {

        Optional<GameDeveloper> developerData =
                Optional.ofNullable(gameDeveloperRepository.findById(id));

        if (developerData.isPresent()) {
            return new ResponseEntity<>(
                    new GameDeveloperOrPublisherResponseDto(
                            developerData.get().getId(),
                            developerData.get().getName(),
                            developerData.get().getEstablishDate()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/developers")
    public ResponseEntity<GameDeveloper> createNewDeveloper(@RequestBody GameDeveloperOrPublisherPostDto gameDeveloperPostDto) {

        try {
            DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

            Date establishDate =
                    DateUtils
                            .addDays(
                                    Date.from(LocalDate.parse(gameDeveloperPostDto.getEstablishDate(), dtfOutput)
                                            .atStartOfDay(ZoneId.of( "Asia/Kolkata" )).toInstant()), 1);

            GameDeveloper newDeveloper =
                    gameDeveloperRepository.save(
                            new GameDeveloper(
                                    gameDeveloperPostDto.getName(),
                                    establishDate
                            )
                    );

            return new ResponseEntity<>(newDeveloper, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/developers/{id}")
    public ResponseEntity<HttpStatus> deleteDeveloperById(@PathVariable("id") long id) {

        try {

            Optional<GameDeveloper> gameDeveloper = Optional.ofNullable(gameDeveloperRepository.findById(id));
            Optional<GameDeveloper> noneDeveloper = gameDeveloperRepository.findByName("None");

            if (gameDeveloper.isPresent()) {

                List<VideoGame> videoGames = new ArrayList<VideoGame>();
                videoGameRepository.findAll().forEach(videoGames::add);

                videoGames =
                        videoGames.stream()
                                .filter(game -> game.getGameDeveloper().getName().equals(gameDeveloper.get().getName())).collect(Collectors.toList());

                if (videoGames.size() != 0) {
                    for (VideoGame videoGame : videoGames) {

                        videoGame.setGameDeveloper(noneDeveloper.get());
                        videoGameRepository.save(videoGame);
                    }
                }
            }

            gameDeveloperRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/developers")
    public ResponseEntity<HttpStatus> deleteAllDevelopers() {

        try {
            gameDeveloperRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
