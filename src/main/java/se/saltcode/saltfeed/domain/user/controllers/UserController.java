package se.saltcode.saltfeed.domain.user.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se.saltcode.saltfeed.domain.user.dtos.UpdateUserRequest;
import se.saltcode.saltfeed.domain.user.dtos.UserResponse;
import se.saltcode.saltfeed.domain.user.mappers.UserMapper;
import se.saltcode.saltfeed.domain.user.services.UserService;
import se.saltcode.saltfeed.security.JwtTokenService;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;
    private final JwtTokenService tokenService;

    public UserController(UserService service, JwtTokenService tokenService) {
        this.userService = service;
        this.tokenService = tokenService;
    }

    @GetMapping
    public UserResponse currentUser(@AuthenticationPrincipal(expression = "subject") String email) {
        var loggedInUser = userService.getUserByEmail(email);
        var token = tokenService.generateToken(loggedInUser);

        return UserMapper.mapToUserResponse(loggedInUser, token);
    }

    @PutMapping
    public UserResponse updateUser(
            @Valid @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        loggedInUser = userService.updateUser(request, loggedInUser);
        var token = tokenService.generateToken(loggedInUser);

        return UserMapper.mapToUserResponse(loggedInUser, token);
    }
}
