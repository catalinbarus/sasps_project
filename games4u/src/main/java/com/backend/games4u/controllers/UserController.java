package com.backend.games4u.controllers;

import com.backend.games4u.message.*;
import com.backend.games4u.models.*;
import com.backend.games4u.repository.UserImageRepository;
import com.backend.games4u.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserImageRepository userImageRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam(required = false) String userName) {

        try {

            List<User> users = new ArrayList<User>();

            userRepository.findAll().forEach(users::add);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            if (userName != null) {
                users =
                        users.stream().filter(user -> user.getUsername().toLowerCase().contains(userName.toLowerCase())).collect(Collectors.toList());
            }

            List<UserResponseDto> userResponseDtos = new ArrayList<>();

            for (User user : users) {

                Optional<UserImage> userImage = userImageRepository.findByUser(user);

                UserImageResponseDto userImageResponseDto = new UserImageResponseDto();
                MyGameListConfigResponseDto myGameListConfigResponseDto =
                        new MyGameListConfigResponseDto();

                if (user.getMyGameListConfig() != null) {
                    myGameListConfigResponseDto.setId(user.getMyGameListConfig().getId());
                    myGameListConfigResponseDto.setUserId(user.getId());
                    myGameListConfigResponseDto.setBackgroundColor(user.getMyGameListConfig().getBackgroundColor());
                    myGameListConfigResponseDto.setTextColor(user.getMyGameListConfig().getTextColor());
                }

                if (userImage.isPresent()) {

                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/profile_pics/")
                            .path(String.valueOf(userImage.get().getId()))
                            .toUriString();

                    String convertedData = Base64.encodeBase64String(userImage.get().getData());


                    userImageResponseDto.setName(userImage.get().getName());
                    userImageResponseDto.setUrl(fileDownloadUri);
                    userImageResponseDto.setType(userImage.get().getType());
                    userImageResponseDto.setSize(userImage.get().getData().length);
                    userImageResponseDto.setUserId(user.getId());
                    userImageResponseDto.setConvertedData(convertedData);
                }

                Set<MyGameListEntryResponseDto> myGameListEntryResponseDtos =
                        user.getMyGameListEntries()
                                .stream()
                                .flatMap(g -> Stream.of(
                                        new MyGameListEntryResponseDto(
                                                g.getId(),
                                                g.getVideoGame().getId(),
                                                g.getUser().getId(),
                                                g.getMyGameListStatus().getId(),
                                                g.getCreatedAt())))
                                .collect(Collectors.toSet());

                userResponseDtos.add(
                        new UserResponseDto(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getRoles(),
                                userImageResponseDto,
                                myGameListConfigResponseDto,
                                myGameListEntryResponseDtos)
                );
            }

            return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users_paginated")
    public ResponseEntity<PaginatedUserResponseDto> getAllUsersPaginated(
            @RequestParam(required = false) String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {

            List<User> users = new ArrayList<User>();

            Pageable paging = PageRequest.of(page, size);
            Page<User> pageUsers;

            if (userName == null)
                pageUsers = userRepository.findAll(paging);
            else
                pageUsers = userRepository.findUserByUsernameContainsIgnoreCase(userName, paging);

            users = pageUsers.getContent();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<UserResponseDto> userResponseDtos = new ArrayList<>();

            for (User user : users) {

                Optional<UserImage> userImage = userImageRepository.findByUser(user);

                UserImageResponseDto userImageResponseDto = new UserImageResponseDto();
                MyGameListConfigResponseDto myGameListConfigResponseDto =
                        new MyGameListConfigResponseDto();

                if (user.getMyGameListConfig() != null) {
                    myGameListConfigResponseDto.setId(user.getMyGameListConfig().getId());
                    myGameListConfigResponseDto.setUserId(user.getId());
                    myGameListConfigResponseDto.setBackgroundColor(user.getMyGameListConfig().getBackgroundColor());
                    myGameListConfigResponseDto.setTextColor(user.getMyGameListConfig().getTextColor());
                }

                if (userImage.isPresent()) {

                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/profile_pics/")
                            .path(String.valueOf(userImage.get().getId()))
                            .toUriString();

                    String convertedData = Base64.encodeBase64String(userImage.get().getData());


                    userImageResponseDto.setName(userImage.get().getName());
                    userImageResponseDto.setUrl(fileDownloadUri);
                    userImageResponseDto.setType(userImage.get().getType());
                    userImageResponseDto.setSize(userImage.get().getData().length);
                    userImageResponseDto.setUserId(user.getId());
                    userImageResponseDto.setConvertedData(convertedData);
                }

                Set<MyGameListEntryResponseDto> myGameListEntryResponseDtos =
                        user.getMyGameListEntries()
                                .stream()
                                .flatMap(g -> Stream.of(
                                        new MyGameListEntryResponseDto(
                                                g.getId(),
                                                g.getVideoGame().getId(),
                                                g.getUser().getId(),
                                                g.getMyGameListStatus().getId(),
                                                g.getCreatedAt())))
                                .collect(Collectors.toSet());

                userResponseDtos.add(
                        new UserResponseDto(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getRoles(),
                                userImageResponseDto,
                                myGameListConfigResponseDto,
                                myGameListEntryResponseDtos)
                );
            }

            PaginatedUserResponseDto paginatedUserResponseDto
                    = new PaginatedUserResponseDto(
                    pageUsers.getTotalElements(),
                    pageUsers.getTotalPages(),
                    pageUsers.getNumber(),
                    userResponseDtos);

            return new ResponseEntity<>(paginatedUserResponseDto, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") long id) {

        try {
            Optional<User> user = userRepository.findById(id);

            if (user.isPresent()) {

                Optional<UserImage> userImage = userImageRepository.findByUser(user.get());

                UserImageResponseDto userImageResponseDto = new UserImageResponseDto();
                MyGameListConfigResponseDto myGameListConfigResponseDto =
                        new MyGameListConfigResponseDto();

                if (user.get().getMyGameListConfig() != null) {
                    myGameListConfigResponseDto.setId(user.get().getMyGameListConfig().getId());
                    myGameListConfigResponseDto.setUserId(user.get().getId());
                    myGameListConfigResponseDto.setBackgroundColor(user.get().getMyGameListConfig().getBackgroundColor());
                    myGameListConfigResponseDto.setTextColor(user.get().getMyGameListConfig().getTextColor());
                }

                if (userImage.isPresent()) {

                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/profile_pics/")
                            .path(String.valueOf(userImage.get().getId()))
                            .toUriString();

                    String convertedData = Base64.encodeBase64String(userImage.get().getData());


                    userImageResponseDto.setName(userImage.get().getName());
                    userImageResponseDto.setUrl(fileDownloadUri);
                    userImageResponseDto.setType(userImage.get().getType());
                    userImageResponseDto.setSize(userImage.get().getData().length);
                    userImageResponseDto.setUserId(user.get().getId());
                    userImageResponseDto.setConvertedData(convertedData);
                }

                Set<MyGameListEntryResponseDto> myGameListEntryResponseDtos =
                        user.get().getMyGameListEntries()
                                .stream()
                                .flatMap(g -> Stream.of(
                                        new MyGameListEntryResponseDto(
                                                g.getId(),
                                                g.getVideoGame().getId(),
                                                g.getUser().getId(),
                                                g.getMyGameListStatus().getId(),
                                                g.getCreatedAt())))
                                .collect(Collectors.toSet());

                UserResponseDto userResponseDto =
                        new UserResponseDto(
                                user.get().getId(),
                                user.get().getUsername(),
                                user.get().getEmail(),
                                user.get().getRoles(),
                                userImageResponseDto,
                                myGameListConfigResponseDto,
                                myGameListEntryResponseDtos
                        );

                return new ResponseEntity<>(userResponseDto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") long id) {

        try {
            Optional<User> user = userRepository.findById(id);

            if (user.isPresent()) {

                userRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
