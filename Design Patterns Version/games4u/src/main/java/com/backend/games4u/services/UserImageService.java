package com.backend.games4u.services;

import com.backend.games4u.message.UserImageResponseDto;
import com.backend.games4u.models.User;
import com.backend.games4u.models.UserImage;
import com.backend.games4u.repository.UserImageRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@Service
public class UserImageService {
    @Autowired
    private UserImageRepository userImageRepository;

    public UserImageResponseDto getUserImage(User user) {
        Optional<UserImage> userImageOpt = userImageRepository.findByUser(user);
        UserImageResponseDto userImageDto = new UserImageResponseDto();

        if (userImageOpt.isPresent()) {
            UserImage userImage = userImageOpt.get();
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/profile_pics/")
                    .path(String.valueOf(userImage.getId()))
                    .toUriString();

            String convertedData = Base64.encodeBase64String(userImage.getData());

            userImageDto.setName(userImage.getName());
            userImageDto.setUrl(fileDownloadUri);
            userImageDto.setType(userImage.getType());
            userImageDto.setSize(userImage.getData().length);
            userImageDto.setUserId(user.getId());
            userImageDto.setConvertedData(convertedData);
        }

        return userImageDto;
    }
}

