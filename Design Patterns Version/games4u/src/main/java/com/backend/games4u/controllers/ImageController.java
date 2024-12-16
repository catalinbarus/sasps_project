package com.backend.games4u.controllers;

import com.backend.games4u.message.ImageResponseDto;
import com.backend.games4u.message.ImageResponseMessage;
import com.backend.games4u.models.ImageDB;
import com.backend.games4u.models.Review;
import com.backend.games4u.repository.ImageDBRepository;
import com.backend.games4u.service.ImageStorageService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
public class ImageController {

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ImageDBRepository imageDBRepository;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("game_id") Long id) {
        String message = "";
        try {
            imageStorageService.store(file, id);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ImageResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImageResponseMessage(message));
        }
    }

    @PostMapping("/upload_boxart")
    public ResponseEntity<ImageResponseMessage> uploadBoxArt(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("game_id") Long id) {
        String message = "";
        try {
            imageStorageService.storeBoxart(file, id);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ImageResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImageResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ImageResponseDto>> getListFiles() {
        List<ImageResponseDto> files = imageStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(dbFile.getId()))
                    .toUriString();

            String convertedData = Base64.encodeBase64String(dbFile.getData());

            return new ImageResponseDto(
                    dbFile.getId(),
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getVideoGame().getId(),
                    convertedData);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable long id) {
        ImageDB fileDB = imageStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<HttpStatus> deleteImageById(@PathVariable("id") long id) {

        try {
            Optional<ImageDB> gameImage = imageDBRepository.findById(id);

            if (gameImage.isPresent()) {
                imageDBRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
