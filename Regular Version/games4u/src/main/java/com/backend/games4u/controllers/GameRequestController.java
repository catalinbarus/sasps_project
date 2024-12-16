package com.backend.games4u.controllers;

import com.backend.games4u.message.GameRequestPostDto;
import com.backend.games4u.message.GameRequestResponseDto;
import com.backend.games4u.message.PaginatedGameRequestResponseDto;
import com.backend.games4u.message.PaginatedGenreResponseDto;
import com.backend.games4u.models.GameRequest;
import com.backend.games4u.models.Genre;
import com.backend.games4u.models.Trailer;
import com.backend.games4u.models.User;
import com.backend.games4u.repository.GameRequestRepository;
import com.backend.games4u.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class GameRequestController {

    @Autowired
    private GameRequestRepository gameRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/requests")
    public ResponseEntity<PaginatedGameRequestResponseDto> getAllRequests(
            @RequestParam(required = false) String gameName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {

            List<GameRequestResponseDto> gameRequestResponseDtos = new ArrayList<>();
            List<GameRequest> gameRequests = new ArrayList<GameRequest>();

            Pageable paging = PageRequest.of(page, size);
            Page<GameRequest> pageRequests;

            if (gameName == null)
                pageRequests = gameRequestRepository.findAll(paging);
            else
                pageRequests =
                        gameRequestRepository.findGameRequestByGameNameContainsIgnoreCase(gameName, paging);

            gameRequests = pageRequests.getContent();

            if (gameRequests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            for (GameRequest request : gameRequests) {
                GameRequestResponseDto gameRequestResponseDto = new GameRequestResponseDto(
                        request.getId(),
                        request.getGameName(),
                        request.getLink(),
                        request.getDetails(),
                        request.getUser().getId().toString(),
                        request.getCreatedAt()
                );
                gameRequestResponseDtos.add(gameRequestResponseDto);
            }

            PaginatedGameRequestResponseDto paginatedGameRequestResponseDto
                    = new PaginatedGameRequestResponseDto(
                    pageRequests.getTotalElements(),
                    pageRequests.getTotalPages(),
                    pageRequests.getNumber(),
                    gameRequestResponseDtos);

            return new ResponseEntity<>(paginatedGameRequestResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/requests")
    public ResponseEntity<GameRequest> createNewRequest(@RequestBody GameRequestPostDto gameRequestPostDto) {

        try {
            Optional<User> user =
                    userRepository.findById(Long.parseLong(gameRequestPostDto.getUserId()));

            if (user.isPresent()) {

                GameRequest gameRequest =
                        gameRequestRepository.save(
                                new GameRequest(
                                        gameRequestPostDto.getGameName(),
                                        gameRequestPostDto.getLink(),
                                        gameRequestPostDto.getDetails(),
                                        user.get(),
                                        new Date()
                                )
                        );

                return new ResponseEntity<>(gameRequest, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<HttpStatus> deleteRequestById(@PathVariable("id") long id) {

        try {
            Optional<GameRequest> request = gameRequestRepository.findById(id);

            if (request.isPresent()) {
                gameRequestRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
