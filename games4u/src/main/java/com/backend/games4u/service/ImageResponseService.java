package com.backend.games4u.service;

import com.backend.games4u.message.ImageResponseDto;
import com.backend.games4u.models.ImageDB;
import com.backend.games4u.models.VideoGame;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageResponseService {

    @Autowired
    private ImageStorageService imageStorageService;

    public List<ImageResponseDto> getPhotosInTheProperResponse(VideoGame videoGame) {
        return imageStorageService.getAllPhotosFromAGame(videoGame).get().stream().map(dbFile -> {
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
    }

    public ImageResponseDto getBoxartInTheProperResponse(VideoGame videoGame) {

        Optional<ImageDB> imageDB = imageStorageService.getBoxartForAGame(videoGame);

        if (imageDB.isPresent()) {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(imageDB.get().getId()))
                    .toUriString();

            String convertedData = Base64.encodeBase64String(imageDB.get().getData());

            return new ImageResponseDto(
                    imageDB.get().getId(),
                    imageDB.get().getName(),
                    fileDownloadUri,
                    imageDB.get().getType(),
                    imageDB.get().getData().length,
                    imageDB.get().getVideoGame().getId(),
                    convertedData);
        } else {
            return new ImageResponseDto(
                    0,
                    null,
                    null,
                    null,
                    0,
                    0,
                    null);
        }
    }
}
