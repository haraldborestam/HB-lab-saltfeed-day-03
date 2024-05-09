package se.saltcode.saltfeed.domain.user.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.saltcode.saltfeed.domain.user.dtos.CreateUserRequest;
import se.saltcode.saltfeed.domain.user.dtos.LoginRequest;
import se.saltcode.saltfeed.domain.user.dtos.UserResponse;
import se.saltcode.saltfeed.domain.user.mappers.UserMapper;
import se.saltcode.saltfeed.domain.user.services.UserService;
import se.saltcode.saltfeed.security.JwtTokenService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    private final JwtTokenService tokenService;

    @Autowired
    public UsersController(UserService userService, JwtTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> registration(@Valid @RequestBody CreateUserRequest registration) throws URISyntaxException {
        var user = userService.createUser(registration);
        var token = tokenService.generateToken(user);

        return ResponseEntity.created(new URI("/api/profiles/" + user.getProfile().getUsername()))
                .body(UserMapper.mapToUserResponse(user, token));
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        var user = userService.login(request);
        var token = tokenService.generateToken(user);

        return UserMapper.mapToUserResponse(user, token);
    }
}
