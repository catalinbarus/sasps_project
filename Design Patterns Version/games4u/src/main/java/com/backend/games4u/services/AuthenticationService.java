package com.backend.games4u.services;

import com.backend.games4u.message.UserImageResponseDto;
import com.backend.games4u.models.User;
import com.backend.games4u.payload.request.LoginRequest;
import com.backend.games4u.payload.response.UserInfoResponse;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.security.jwt.JwtUtils;
import com.backend.games4u.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserImageService userImageService;
    @Autowired
    private MyGameListConfigService myGameListConfigService;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Optional<User> currentUser = userRepository.findById(userDetails.getId());

        if (currentUser.isPresent()) {
            myGameListConfigService.initializeMyGameListConfigIfNotExists(currentUser.get());
            UserImageResponseDto userImageDto = userImageService.getUserImage(currentUser.get());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new UserInfoResponse(userDetails.getId(),
                            userDetails.getUsername(), userDetails.getEmail(), roles, userImageDto));
        } else {
            return ResponseEntity.badRequest().body("Error: User not found.");
        }
    }
}

