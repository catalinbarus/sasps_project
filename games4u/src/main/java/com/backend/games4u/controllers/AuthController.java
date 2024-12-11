package com.backend.games4u.controllers;


import com.backend.games4u.message.UserImageResponseDto;
import com.backend.games4u.message.UserResponseDto;
import com.backend.games4u.models.*;
import com.backend.games4u.payload.request.LoginRequest;
import com.backend.games4u.payload.request.SignupRequest;
import com.backend.games4u.payload.response.MessageResponse;
import com.backend.games4u.payload.response.UserInfoResponse;
import com.backend.games4u.repository.MyGameListConfigRepository;
import com.backend.games4u.repository.RoleRepository;
import com.backend.games4u.repository.UserImageRepository;
import com.backend.games4u.repository.UserRepository;
import com.backend.games4u.security.jwt.JwtUtils;
import com.backend.games4u.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserImageRepository userImageRepository;

    @Autowired
    MyGameListConfigRepository myGameListConfigRepository;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Optional<User> currentUser = userRepository.findById(userDetails.getId());

        if (currentUser.get().getMyGameListConfig() == null && currentUser.isPresent()) {

            MyGameListConfig myGameListConfig = new MyGameListConfig();
            myGameListConfig.setUser(currentUser.get());
            myGameListConfig.setBackgroundColor("#FFFFFF");
            myGameListConfig.setTextColor("#840428");

            myGameListConfigRepository.save(myGameListConfig);
        }

        Optional<UserImage> userImage = userImageRepository.findByUser(currentUser.get());

        UserImageResponseDto userImageResponseDto = new UserImageResponseDto();

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
            userImageResponseDto.setUserId(userDetails.getId());
            userImageResponseDto.setConvertedData(convertedData);
        }

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles,
                        userImageResponseDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_MODERATOR":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
