package com.backend.games4u.controllers;

import com.backend.games4u.message.ImageResponseDto;
import com.backend.games4u.message.ImageResponseMessage;
import com.backend.games4u.message.UserImageResponseDto;
import com.backend.games4u.models.ImageDB;
import com.backend.games4u.models.User;
import com.backend.games4u.models.UserImage;
import com.backend.games4u.repository.UserImageRepository;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.service.ImageStorageService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true")
@RestController
public class UserImageController {

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    UserImageRepository userImageRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/upload_profile_picture")
    public ResponseEntity<ImageResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("user_id") Long id) {
        String message = "";
        try {
            imageStorageService.storeUserImage(file, id);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ImageResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImageResponseMessage(message));
        }
    }

    @GetMapping("/user_images")
    public ResponseEntity<List<UserImageResponseDto>> getProfilePictures() {
        List<UserImageResponseDto> files = imageStorageService.getAllProfilePictures().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/profile_pics/")
                    .path(String.valueOf(dbFile.getId()))
                    .toUriString();

            String convertedData = Base64.encodeBase64String(dbFile.getData());

            return new UserImageResponseDto(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getUser().getId(),
                    convertedData);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @DeleteMapping("/user_images/{id}")
    public ResponseEntity<HttpStatus> deleteUserImageById(@PathVariable("id") long id) {

        try {
            Optional<UserImage> userImage = userImageRepository.findById(id);

            if (userImage.isPresent()) {

                Optional<User> user = userRepository.findUserByUserImage(userImage.get());
                user.get().setUserImage(null);

                userImageRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
