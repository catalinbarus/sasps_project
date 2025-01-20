package com.backend.games4u.controllers;

import com.backend.games4u.message.*;
import com.backend.games4u.models.*;
import com.backend.games4u.services.MyGameListEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class MyGameListEntryController {

    private final MyGameListEntryService myGameListEntryService;

    @Autowired
    public MyGameListEntryController(MyGameListEntryService myGameListEntryService) {
        this.myGameListEntryService = myGameListEntryService;
    }

    @GetMapping("/game_list_entries/filter")
    public ResponseEntity<List<MyGameListEntryResponseDto>> getEntriesOfUser(
            @RequestParam("user_id") long userId,
            @RequestParam(name = "status_id", required = false) Optional<Long> statusId) {
        return myGameListEntryService.getEntriesOfUser(userId, statusId);
    }

    @GetMapping("/game_list_entries/recent_updates")
    public ResponseEntity<PaginatedMyGameListEntryResponseDto> getRecentUpdatesOfEntries(
            @RequestParam("user_id") long userId,
            @RequestParam(name = "status_id", required = false) Optional<Long> statusId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        return myGameListEntryService.getRecentUpdatesOfEntries(userId, statusId, page, size, sort);
    }

    @GetMapping("/game_list_entries/filter_by_game")
    public ResponseEntity<MyGameListEntryResponseDto> getEntryForSpecificGame(
            @RequestParam("user_id") long userId,
            @RequestParam("game_id") long gameId) {
        return myGameListEntryService.getEntryForSpecificGame(userId, gameId);
    }

    @PutMapping("/game_list_entries/{id}")
    public ResponseEntity<MyGameListEntry> createNewGameListEntry(@RequestBody MyGameListEntryPostDto myGameListEntryPostDto) {
        return myGameListEntryService.createNewGameListEntry(myGameListEntryPostDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyGameListEntry> updateGameEntry(
            @PathVariable("id") long entryId,
            @RequestBody MyGameListEntryPutDto entryPutDto) {
        return myGameListEntryService.updateGameEntry(entryId, entryPutDto);
    }
}
