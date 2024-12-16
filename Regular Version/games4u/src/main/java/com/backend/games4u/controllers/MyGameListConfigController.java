package com.backend.games4u.controllers;

import com.backend.games4u.message.MyGameListConfigPutDto;
import com.backend.games4u.models.MyGameListConfig;
import com.backend.games4u.models.MyGameListEntry;
import com.backend.games4u.models.MyGameListStatus;
import com.backend.games4u.repository.MyGameListConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class MyGameListConfigController {

    @Autowired
    MyGameListConfigRepository myGameListConfigRepository;


    @PutMapping("/game_list_configs/{id}")
    public ResponseEntity<MyGameListConfig> updateGameConfig(@PathVariable("id") long configId,
                                                             @RequestBody MyGameListConfigPutDto configPutDto) {

        Optional<MyGameListConfig> myGameListConfig = myGameListConfigRepository.findById(configId);

        if (myGameListConfig.isPresent()) {

            myGameListConfig.get().setBackgroundColor(configPutDto.getBackgroundColor());
            myGameListConfig.get().setTextColor(configPutDto.getTextColor());

            return new ResponseEntity<>(myGameListConfigRepository.save(myGameListConfig.get()), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
