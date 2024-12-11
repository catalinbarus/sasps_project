package com.backend.games4u.service;

import com.backend.games4u.models.ImageDB;
import com.backend.games4u.models.User;
import com.backend.games4u.models.UserImage;
import com.backend.games4u.models.VideoGame;
import com.backend.games4u.repository.ImageDBRepository;
import com.backend.games4u.repository.UserImageRepository;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ImageStorageService {

    @Autowired
    private ImageDBRepository imageDBRepository;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private UserRepository userRepository;

    public String getFileExtension(String input) {
        int lastDotIndex = input.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == input.length() - 1) {
            return "";
        } else {
            return input.substring(lastDotIndex + 1);
        }
    }


    public ImageDB store(MultipartFile file, Long gameID) throws IOException {
        String imageName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println(imageName);

        Optional<VideoGame> videoGame = videoGameRepository.findById(gameID);

        ImageDB imageDB = new ImageDB(imageName, file.getContentType(), file.getBytes(),
                videoGame.get());

        return imageDBRepository.save(imageDB);
    }

    public ImageDB storeBoxart(MultipartFile file, Long gameID) throws IOException {
        String imageName = StringUtils.cleanPath(file.getOriginalFilename());

        Optional<VideoGame> videoGame = videoGameRepository.findById(gameID);

        Optional<List<ImageDB>> potentialImages =
                imageDBRepository.findAllByVideoGame(videoGame.get());

        if (potentialImages.isPresent()) {
            for (ImageDB image : potentialImages.get()) {
                if (image.getName().contains("boxart")) {
                    imageDBRepository.deleteById(image.getId());
                    break;
                }
            }
        }

        ImageDB imageDB =
                new ImageDB(
                        "boxart." + getFileExtension(imageName), file.getContentType(),
                        file.getBytes(),
                        videoGame.get()
                );

        return imageDBRepository.save(imageDB);
    }

    public ImageDB getFile(Long id) {
        return imageDBRepository.findById(id).get();
    }

    public Stream<ImageDB> getAllFiles() {
        return imageDBRepository.findAll().stream();
    }

    public Stream<UserImage> getAllProfilePictures() {
        return userImageRepository.findAll().stream();
    }

    public Optional<List<ImageDB>> getAllPhotosFromAGame(VideoGame videoGame) {
        return imageDBRepository
                .findAllByVideoGame(videoGame);
    }

    public Optional<ImageDB> getBoxartForAGame(VideoGame videoGame) {
        return imageDBRepository
                .findByVideoGameAndNameStartsWith(videoGame, "boxart");
    }

    public UserImage storeUserImage(MultipartFile file, Long userId) throws IOException {
        String imageName = StringUtils.cleanPath(file.getOriginalFilename());

        Optional<User> user = userRepository.findById(userId);
        Optional<UserImage> potentialImage;

        if (user.isPresent()) {
            potentialImage = userImageRepository.findByUser(user.get());

            if (potentialImage.isPresent()) {
                potentialImage.get().setData(file.getBytes());
                potentialImage.get().setName(imageName);
                potentialImage.get().setType(file.getContentType());

                return userImageRepository.save(potentialImage.get());
            }
        }


        UserImage userImage = new UserImage(imageName, file.getContentType(), file.getBytes(),
                user.get());
        return userImageRepository.save(userImage);
    }
}
