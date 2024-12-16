package com.backend.games4u.controllers;

import com.backend.games4u.message.ReviewPostDto;
import com.backend.games4u.message.TrailerPostDto;
import com.backend.games4u.models.Review;
import com.backend.games4u.models.Trailer;
import com.backend.games4u.models.User;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.repository.TrailerRepository;
import com.backend.games4u.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class TrailerController {

    @Autowired
    VideoGameRepository videoGameRepository;

    @Autowired
    TrailerRepository trailerRepository;

    @PostMapping("/trailers")
    public ResponseEntity<Trailer> createNewTrailer(@RequestBody TrailerPostDto trailer) {

        try {
            Optional<VideoGame> videoGame =
                    Optional.ofNullable(videoGameRepository.findById(Long.parseLong(trailer.getGameId())));

            if (videoGame.isPresent()) {

                Trailer newTrailer =
                        trailerRepository.save(
                                new Trailer(
                                        trailer.getYoutubeId(),
                                        videoGame.get()
                                )
                        );

                return new ResponseEntity<>(newTrailer, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/trailers/{id}")
    public ResponseEntity<HttpStatus> deleteTrailerById(@PathVariable("id") long id) {

        try {
            Optional<Trailer> trailer = trailerRepository.findById(id);

            if (trailer.isPresent()) {
                trailerRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
