package se.saltcode.saltfeed.domain.user.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.saltcode.saltfeed.domain.user.dtos.ProfileResponse;
import se.saltcode.saltfeed.domain.user.mappers.ProfileMapper;
import se.saltcode.saltfeed.domain.user.services.ProfileService;
import se.saltcode.saltfeed.domain.user.services.UserService;

@RestController
@RequestMapping("/api/profiles")
@SecurityRequirement(name = "Bearer Authentication")
public class ProfilesController {

    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    public ProfilesController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ProfileResponse getProfile(@PathVariable String username, @AuthenticationPrincipal Jwt jwt) {
        var loggedInUser = userService.getUser(jwt);
        var profile = profileService.getProfile(username);

        return ProfileMapper.mapToProfileResponse(profile, loggedInUser);
    }

    @PostMapping("/{username}/follow")
    public ProfileResponse followUser(
            @PathVariable String username,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        var profile = profileService.followProfile(username, loggedInUser);

        return ProfileMapper.mapToProfileResponse(profile, loggedInUser);
    }

    @DeleteMapping("/{username}/follow")
    public ProfileResponse unfollowUser(
            @PathVariable String username,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        var profile = profileService.unfollowProfile(username, loggedInUser);

        return ProfileMapper.mapToProfileResponse(profile, loggedInUser);
    }
}
