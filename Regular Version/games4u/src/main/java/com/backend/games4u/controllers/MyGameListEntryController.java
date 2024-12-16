package com.backend.games4u.controllers;

import com.backend.games4u.message.*;
import com.backend.games4u.models.*;
import com.backend.games4u.repository.MyGameListEntryRepository;
import com.backend.games4u.repository.MyGameListStatusRepository;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class MyGameListEntryController {

    @Autowired
    MyGameListEntryRepository myGameListEntryRepository;

    @Autowired
    VideoGameRepository videoGameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyGameListStatusRepository myGameListStatusRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping("/game_list_entries/filter")
    public ResponseEntity<List<MyGameListEntryResponseDto>> getEntriesOfUser(
            @RequestParam("user_id") long userId,
            @RequestParam(name = "status_id", required = false) Optional<Long> statusId) {

        try {
            Optional<User> user = userRepository.findById(userId);
            List<MyGameListEntryResponseDto> myGameListEntryResponseDtos = new ArrayList<>();

            if (statusId.isPresent()) {

                Optional<MyGameListStatus> myGameListStatus =
                        myGameListStatusRepository.findById(statusId.get());

                if (myGameListStatus.isPresent()) {

                    Optional<List<MyGameListEntry>> myGameListEntries
                            =
                            myGameListEntryRepository.findAllByUserAndMyGameListStatus(
                                    user.get(),
                                    myGameListStatus.get());

                    for (MyGameListEntry myGameListEntry : myGameListEntries.get()) {

                        MyGameListEntryResponseDto myGameListEntryResponseDto =
                                new MyGameListEntryResponseDto(
                                        myGameListEntry.getId(),
                                        myGameListEntry.getVideoGame().getId(),
                                        myGameListEntry.getUser().getId(),
                                        myGameListEntry.getMyGameListStatus().getId(),
                                        myGameListEntry.getCreatedAt()
                                );

                        myGameListEntryResponseDtos.add(myGameListEntryResponseDto);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                Optional<List<MyGameListEntry>> myGameListEntries
                        =
                        myGameListEntryRepository.findAllByUser(user.get());

                for (MyGameListEntry myGameListEntry : myGameListEntries.get()) {

                    MyGameListEntryResponseDto myGameListEntryResponseDto =
                            new MyGameListEntryResponseDto(
                                    myGameListEntry.getId(),
                                    myGameListEntry.getVideoGame().getId(),
                                    myGameListEntry.getUser().getId(),
                                    myGameListEntry.getMyGameListStatus().getId(),
                                    myGameListEntry.getCreatedAt()
                            );

                    myGameListEntryResponseDtos.add(myGameListEntryResponseDto);
                }
            }

            return new ResponseEntity<>(myGameListEntryResponseDtos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/game_list_entries/recent_updates")
    public ResponseEntity<PaginatedMyGameListEntryResponseDto> getRecentUpdatesOfEntries(
            @RequestParam("user_id") long userId,
            @RequestParam(name = "status_id", required = false) Optional<Long> statusId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        try {
            Optional<User> user = userRepository.findById(userId);
            List<MyGameListEntryResponseDto> myGameListEntryResponseDtos = new ArrayList<>();
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<MyGameListEntry> entries = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size, Sort.by(orders));
            Page<MyGameListEntry> pageEntries;

            if (statusId.isPresent()) {

                Optional<MyGameListStatus> myGameListStatus =
                        myGameListStatusRepository.findById(statusId.get());

                if (myGameListStatus.isPresent()) {

                    pageEntries = myGameListEntryRepository
                            .findMyGameListEntryByUserAndAndMyGameListStatus(
                                    user.get(),
                                    myGameListStatus.get(), paging);

                    entries = pageEntries.getContent();

                    if (entries.isEmpty()) {
                        PaginatedMyGameListEntryResponseDto paginatedMyGameListEntryResponseDto
                                = new PaginatedMyGameListEntryResponseDto(
                                pageEntries.getTotalElements(),
                                pageEntries.getTotalPages(),
                                pageEntries.getNumber(),
                                myGameListEntryResponseDtos);

                        return new ResponseEntity<>(paginatedMyGameListEntryResponseDto, HttpStatus.OK);
                    }

                    for (MyGameListEntry myGameListEntry : entries) {

                        MyGameListEntryResponseDto myGameListEntryResponseDto =
                                new MyGameListEntryResponseDto(
                                        myGameListEntry.getId(),
                                        myGameListEntry.getVideoGame().getId(),
                                        myGameListEntry.getUser().getId(),
                                        myGameListEntry.getMyGameListStatus().getId(),
                                        myGameListEntry.getCreatedAt()
                                );

                        myGameListEntryResponseDtos.add(myGameListEntryResponseDto);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {

                pageEntries = myGameListEntryRepository.findMyGameListEntryByUser(user.get(), paging);

                entries = pageEntries.getContent();

                if (entries.isEmpty()) {
                    PaginatedMyGameListEntryResponseDto paginatedMyGameListEntryResponseDto
                            = new PaginatedMyGameListEntryResponseDto(
                            pageEntries.getTotalElements(),
                            pageEntries.getTotalPages(),
                            pageEntries.getNumber(),
                            myGameListEntryResponseDtos);

                    return new ResponseEntity<>(paginatedMyGameListEntryResponseDto, HttpStatus.OK);
                }

                for (MyGameListEntry myGameListEntry : entries) {

                    MyGameListEntryResponseDto myGameListEntryResponseDto =
                            new MyGameListEntryResponseDto(
                                    myGameListEntry.getId(),
                                    myGameListEntry.getVideoGame().getId(),
                                    myGameListEntry.getUser().getId(),
                                    myGameListEntry.getMyGameListStatus().getId(),
                                    myGameListEntry.getCreatedAt()
                            );

                    myGameListEntryResponseDtos.add(myGameListEntryResponseDto);
                }
            }

            PaginatedMyGameListEntryResponseDto paginatedMyGameListEntryResponseDto
                    = new PaginatedMyGameListEntryResponseDto(
                    pageEntries.getTotalElements(),
                    pageEntries.getTotalPages(),
                    pageEntries.getNumber(),
                    myGameListEntryResponseDtos);

            return new ResponseEntity<>(paginatedMyGameListEntryResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/game_list_entries/filter_by_game")
    public ResponseEntity<MyGameListEntryResponseDto> getEntryForSpecificGame(
            @RequestParam("user_id") long userId,
            @RequestParam("game_id") long gameId) {

        try {
            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(gameId));
            Optional<User> user = userRepository.findById(userId);
            MyGameListEntryResponseDto myGameListEntryResponseDto =
                    new MyGameListEntryResponseDto();

            if (videoGame.isPresent() && user.isPresent()) {

                Optional<MyGameListEntry> myGameListEntry =
                        myGameListEntryRepository.findMyGameListEntryByVideoGameAndUser(videoGame.get(), user.get());

                if (myGameListEntry.isPresent()) {
                    myGameListEntryResponseDto.setId(myGameListEntry.get().getId());
                    myGameListEntryResponseDto.setVideoGameId(myGameListEntry.get().getVideoGame().getId());
                    myGameListEntryResponseDto.setUserId(myGameListEntry.get().getUser().getId());
                    myGameListEntryResponseDto.setTimestamp(myGameListEntry.get().getCreatedAt());
                    myGameListEntryResponseDto.setMyGameListStatusId(myGameListEntry.get().getMyGameListStatus().getId());
                }
            }

            return new ResponseEntity<>(myGameListEntryResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/game_list_entries")
    public ResponseEntity<MyGameListEntry> createNewGameListEntry(@RequestBody MyGameListEntryPostDto myGameListEntryPostDto) {

        try {
            Optional<VideoGame> videoGame =
                    Optional.ofNullable(videoGameRepository.findById(myGameListEntryPostDto.getVideoGameId()));

            Optional<User> user = userRepository.findById(myGameListEntryPostDto.getUserId());

            Optional<MyGameListStatus> myGameListStatus =
                    myGameListStatusRepository.findById(myGameListEntryPostDto.getMyGameListStatusId());

            if (videoGame.isPresent() && user.isPresent() && myGameListStatus.isPresent()) {

                MyGameListEntry newGameListEntry =
                        myGameListEntryRepository.save(
                                new MyGameListEntry(
                                        videoGame.get(),
                                        user.get(),
                                        myGameListStatus.get(),
                                        new Date()
                                )
                        );

                return new ResponseEntity<>(newGameListEntry, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/game_list_entries/{id}")
    public ResponseEntity<MyGameListEntry> updateGameEntry(@PathVariable("id") long entryId,
                                               @RequestBody MyGameListEntryPutDto entryPutDto) {

        Optional<MyGameListEntry> myGameListEntry = myGameListEntryRepository.findById(entryId);

        if (myGameListEntry.isPresent()) {

            Optional<MyGameListStatus> myGameListStatus =
                    myGameListStatusRepository.findById(entryPutDto.getMyGameListStatusId());

            myGameListEntry.get().setMyGameListStatus(myGameListStatus.get());

            return new ResponseEntity<>(myGameListEntryRepository.save(myGameListEntry.get()), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
