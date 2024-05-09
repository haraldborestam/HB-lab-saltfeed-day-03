package se.saltcode.saltfeed.domain.user.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.saltcode.saltfeed.config.TestConfig;
import se.saltcode.saltfeed.domain.user.dtos.ProfileResponse;
import se.saltcode.saltfeed.domain.user.models.User;
import se.saltcode.saltfeed.domain.user.services.ProfileService;
import se.saltcode.saltfeed.domain.user.services.UserService;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfilesController.class)
@ContextConfiguration(classes = TestConfig.class)
class ProfilesControllerTest {

    private static final String USER_NAME = "testUser";
    private static final User testUser = new User();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProfileService profileService;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        testUser.setEmail("test@example.com");
        testUser.getProfile().setUsername(USER_NAME);
        testUser.getProfile().setBio("A simple bio");
        testUser.getProfile().setImage("https://example.com/image");
        testUser.setPassword("{noop}12345678}");
    }

    @Test
    void shouldReturnOkAndAProfileWhenAUserIsFound() throws Exception {
        // Arrange
        when(profileService.getProfile(anyString()))
                .thenReturn(testUser.getProfile());
        // Act & Assert
        mockMvc.perform(get("/api/profiles/" + USER_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile", Matchers.notNullValue(ProfileResponse.class)));
    }

    @Test
    void shouldReturnNotFoundWhenUserNotFound() throws Exception {
        // Arrange
        when(profileService.getProfile(anyString()))
                .thenThrow(new NoSuchElementException("User not found"));
        // Act & Assert
        mockMvc.perform(get("/api/profiles/" + USER_NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkAndAProfileWhenFollowingAUser() throws Exception {
        // Arrange
        when(profileService.followProfile(anyString(), nullable(User.class)))
                .thenReturn(testUser.getProfile());
        // Act & Assert
        mockMvc.perform(post("/api/profiles/" + USER_NAME + "/follow")
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenFollowingAUserThatIsNotFound() throws Exception {
        // Arrange
        when(profileService.followProfile(anyString(), nullable(User.class)))
                .thenThrow(new NoSuchElementException("User not found"));
        // Act & Assert
        mockMvc.perform(post("/api/profiles/" + USER_NAME + "/follow")
                        .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUnauthorizedWhenNotLoggedInAndTryFollowingUser() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/profiles/" + USER_NAME + "/follow"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnOkAndAProfileWhenUnfollowingAUser() throws Exception {
        // Arrange
        when(profileService.unfollowProfile(anyString(), nullable(User.class)))
                .thenReturn(testUser.getProfile());
        // Act & Assert
        mockMvc.perform(delete("/api/profiles/" + USER_NAME + "/follow")
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenUnfollowingUserThatIsNotFound() throws Exception {
        // Arrange
        when(profileService.unfollowProfile(anyString(), nullable(User.class)))
                .thenThrow(new NoSuchElementException("User not found"));
        // Act & Assert
        mockMvc.perform(delete("/api/profiles/" + USER_NAME + "/follow")
                        .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUnauthorizedWhenNotLoggedInAndTryingToUnfollow() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/profiles/" + USER_NAME + "/follow"))
                .andExpect(status().isUnauthorized());
    }
}

