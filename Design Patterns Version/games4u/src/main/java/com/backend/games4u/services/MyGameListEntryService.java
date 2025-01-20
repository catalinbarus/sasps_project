package com.backend.games4u.services;

import com.backend.games4u.message.MyGameListEntryPostDto;
import com.backend.games4u.message.MyGameListEntryPutDto;
import com.backend.games4u.message.MyGameListEntryResponseDto;
import com.backend.games4u.message.PaginatedMyGameListEntryResponseDto;
import com.backend.games4u.models.MyGameListEntry;
import com.backend.games4u.models.MyGameListStatus;
import com.backend.games4u.models.User;
import com.backend.games4u.models.VideoGame;
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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyGameListEntryService {

    @Autowired
    private MyGameListEntryRepository myGameListEntryRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyGameListStatusRepository myGameListStatusRepository;

    public ResponseEntity<List<MyGameListEntryResponseDto>> getEntriesOfUser(long userId, Optional<Long> statusId) {
        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<MyGameListEntry> entries = statusId.map(
                    id -> myGameListStatusRepository.findById(id)
                            .map(status -> myGameListEntryRepository.findAllByUserAndMyGameListStatus(user.get(), status).orElse(new ArrayList<>()))
                            .orElse(new ArrayList<>())
            ).orElse(myGameListEntryRepository.findAllByUser(user.get()).orElse(new ArrayList<>()));

            List<MyGameListEntryResponseDto> dtos = entries.stream()
                    .map(this::mapToDto)
                    .toList();

            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<PaginatedMyGameListEntryResponseDto> getRecentUpdatesOfEntries(
            long userId, Optional<Long> statusId, int page, int size, String[] sort) {
        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Pageable pageable = getPageable(sort, page, size);
            Page<MyGameListEntry> pageEntries = statusId.map(
                    id -> myGameListStatusRepository.findById(id)
                            .map(status -> myGameListEntryRepository.findMyGameListEntryByUserAndAndMyGameListStatus(user.get(), status, pageable))
                            .orElse(Page.empty(pageable))
            ).orElse(myGameListEntryRepository.findMyGameListEntryByUser(user.get(), pageable));

            List<MyGameListEntryResponseDto> dtos = pageEntries.stream()
                    .map(this::mapToDto)
                    .toList();

            return ResponseEntity.ok(new PaginatedMyGameListEntryResponseDto(
                    pageEntries.getTotalElements(),
                    pageEntries.getTotalPages(),
                    pageEntries.getNumber(),
                    dtos
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<MyGameListEntryResponseDto> getEntryForSpecificGame(long userId, long gameId) {
        try {
            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(gameId));
            Optional<User> user = userRepository.findById(userId);

            if (videoGame.isEmpty() || user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return myGameListEntryRepository.findMyGameListEntryByVideoGameAndUser(videoGame.get(), user.get())
                    .map(this::mapToDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<MyGameListEntry> createNewGameListEntry(MyGameListEntryPostDto dto) {
        try {
            Optional<VideoGame> videoGame = Optional.ofNullable(videoGameRepository.findById(dto.getVideoGameId()));
            Optional<User> user = userRepository.findById(dto.getUserId());
            Optional<MyGameListStatus> status = myGameListStatusRepository.findById(dto.getMyGameListStatusId());

            if (videoGame.isEmpty() || user.isEmpty() || status.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            MyGameListEntry entry = new MyGameListEntry(videoGame.get(), user.get(), status.get(), new Date());
            MyGameListEntry savedEntry = myGameListEntryRepository.save(entry);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<MyGameListEntry> updateGameEntry(long entryId, MyGameListEntryPutDto dto) {
        return myGameListEntryRepository.findById(entryId)
                .map(entry -> {
                    myGameListStatusRepository.findById(dto.getMyGameListStatusId())
                            .ifPresent(entry::setMyGameListStatus);
                    return ResponseEntity.ok(myGameListEntryRepository.save(entry));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private Pageable getPageable(String[] sort, int page, int size) {
        List<Sort.Order> orders = Arrays.stream(sort)
                .map(s -> s.split(","))
                .map(arr -> new Sort.Order(Sort.Direction.fromString(arr[1]), arr[0]))
                .toList();
        return PageRequest.of(page, size, Sort.by(orders));
    }

    private MyGameListEntryResponseDto mapToDto(MyGameListEntry entry) {
        return new MyGameListEntryResponseDto(
                entry.getId(),
                entry.getVideoGame().getId(),
                entry.getUser().getId(),
                entry.getMyGameListStatus().getId(),
                entry.getCreatedAt()
        );
    }
}
