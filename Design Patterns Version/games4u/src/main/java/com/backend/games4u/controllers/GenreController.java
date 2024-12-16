package com.backend.games4u.controllers;

import com.backend.games4u.message.*;
import com.backend.games4u.models.Genre;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.payload.response.MessageResponse;
import com.backend.games4u.repository.GenreRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class GenreController {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    VideoGameRepository videoGameRepository;

    @GetMapping("/genres")
    public ResponseEntity<List<GenreResponseDto>> getAllPublishers(@RequestParam(required = false) String genreName) {

        try {
            List<GenreResponseDto> genreResponseDtos = new ArrayList<>();

            List<Genre> genres = new ArrayList<Genre>();

            genreRepository.findAll().forEach(genres::add);

            if (genreName != null) {
                genres =
                        genres.stream().filter(genre -> genre.getName().toLowerCase().contains(genreName.toLowerCase())).collect(Collectors.toList());
            }

            if (genres.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Optional<Genre> noneGenre = genreRepository.findByName("None");

            int noneIndex = genres.indexOf(noneGenre.get());
            if (noneIndex != -1) {
                genres.remove(noneIndex);
                genres.add(0, noneGenre.get());
            }

            for (Genre genre : genres) {

                GenreResponseDto genreResponseDto = new GenreResponseDto(genre.getId(), genre.getName());

                genreResponseDtos.add(genreResponseDto);
            }

            return new ResponseEntity<>(genreResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genres_paginated")
    public ResponseEntity<PaginatedGenreResponseDto> getAllPublishersPaginated(
            @RequestParam(required = false) String genreName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            List<GenreResponseDto> genreResponseDtos = new ArrayList<>();
            List<Genre> genres = new ArrayList<Genre>();

            Pageable paging = PageRequest.of(page, size);
            Page<Genre> pageGenres;

            if (genreName == null)
                pageGenres = genreRepository.findAll(paging);
            else
                pageGenres = genreRepository.findGenreByNameContainsIgnoreCase(genreName, paging);

            genres = pageGenres.getContent();

            if (genres.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            for (Genre genre : genres) {
                GenreResponseDto genreResponseDto = new GenreResponseDto(genre.getId(), genre.getName());
                genreResponseDtos.add(genreResponseDto);
            }

            PaginatedGenreResponseDto paginatedGenreResponseDto
                    = new PaginatedGenreResponseDto(
                    pageGenres.getTotalElements(),
                    pageGenres.getTotalPages(),
                    pageGenres.getNumber(),
                    genreResponseDtos);

            return new ResponseEntity<>(paginatedGenreResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") long id) {

        Optional<Genre> genreData = Optional.ofNullable(genreRepository.findById(id));

        if (genreData.isPresent()) {
            return new ResponseEntity<>(genreData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/genres")
    public ResponseEntity<?> createNewGenre(@RequestBody GenrePostDto genrePostDto) {

        try {
            Optional<Genre> genre = genreRepository.findByName(genrePostDto.getGenre());

            if (genre.isPresent()) {
                return ResponseEntity.badRequest().body(new MessageResponse("This genre already " +
                        "exists!"));
            } else {
                Genre newGenre = genreRepository.save(new Genre(genrePostDto.getGenre()));

                return new ResponseEntity<>(newGenre, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity<HttpStatus> deleteGenreById(@PathVariable("id") long id) {
        try {

            Optional<Genre> genre = Optional.ofNullable(genreRepository.findById(id));
            Optional<Genre> noneGenre = genreRepository.findByName("None");

            if (genre.isPresent()) {

                List<VideoGame> videoGames = new ArrayList<VideoGame>();
                videoGameRepository.findAll().forEach(videoGames::add);

                videoGames =
                        videoGames.stream()
                                .filter(game -> game.getGenre().getName().equals(genre.get().getName())).collect(Collectors.toList());

                if (videoGames.size() != 0) {
                    for (VideoGame videoGame : videoGames) {

                        videoGame.setGenre(noneGenre.get());
                        videoGameRepository.save(videoGame);
                    }
                }
            }

            genreRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/genres")
    public ResponseEntity<HttpStatus> deleteAllGenres() {
        try {
            genreRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
