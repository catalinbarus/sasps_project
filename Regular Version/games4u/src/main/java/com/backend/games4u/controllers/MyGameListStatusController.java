package com.backend.games4u.controllers;

import com.backend.games4u.message.MyGameListStatusPostDto;
import com.backend.games4u.models.MyGameListStatus;
import com.backend.games4u.payload.response.MessageResponse;
import com.backend.games4u.repository.MyGameListStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class MyGameListStatusController {

    @Autowired
    MyGameListStatusRepository myGameListStatusRepository;

    @PostMapping("/game_list_statuses")
    public ResponseEntity<?> createNewStatus(@RequestBody MyGameListStatusPostDto myGameListStatusPostDto) {

        try {
            Optional<MyGameListStatus> myGameListStatus
                    = myGameListStatusRepository.findByStatus(myGameListStatusPostDto.getStatus());

            if (myGameListStatus.isPresent()) {
                return ResponseEntity.badRequest().body(new MessageResponse("This status already " +
                        "exists!"));
            } else {
                MyGameListStatus newGameStatus =
                        myGameListStatusRepository
                                .save(new MyGameListStatus(myGameListStatusPostDto.getStatus()));

                return new ResponseEntity<>(newGameStatus, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
