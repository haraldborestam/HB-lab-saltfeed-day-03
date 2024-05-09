package se.saltcode.saltfeed.domain.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.saltcode.saltfeed.config.TestConfig;
import se.saltcode.saltfeed.domain.user.dtos.UpdateUserRequest;
import se.saltcode.saltfeed.domain.user.dtos.UserResponse;
import se.saltcode.saltfeed.domain.user.models.User;
import se.saltcode.saltfeed.domain.user.services.UserService;
import se.saltcode.saltfeed.security.JwtTokenService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = TestConfig.class)
class UserControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenService tokenService;
    @MockBean
    private UserService userService;

    @Test
    void shouldReturnOkAndAUserWhenAUserIsFound() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString()))
                .thenReturn(new User());
        // Act & Assert
        mockMvc.perform(get("/api/user")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user", Matchers.notNullValue(UserResponse.class)));
    }

    @Test
    void shouldReturnUnauthorizedWhenNotLoggedIn() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUpdateLoggedInUser() throws Exception {
        // Arrange
        var request = new UpdateUserRequest(
                "new@email.com",
                "adam",
                "This is a bio",
                "https://image.com/image",
                "12345bacde"
        );
        when(userService.updateUser(eq(request), nullable(User.class)))
                .thenReturn(new User());
        // Act & Assert
        mockMvc.perform(put("/api/user")
                        .with(jwt())
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedWhenUpdatingButNotLoggedIn() throws Exception {
        // Arrange
        var request = new UpdateUserRequest(
                "new@email.com",
                "adam",
                "This is a bio",
                "https://image.com/image",
                "12345bacde"
        );
        // Act & Assert
        mockMvc.perform(put("/api/user")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Nested
    class shouldReturnUnprocessableContentWhenUpdatingUser {

        @Test
        void whenEmailIsInvalid() throws Exception {
            // Arrange
            var request = new UpdateUserRequest(
                    "invalid",
                    "adam",
                    "This is a bio",
                    "https://image.com/image",
                    "12345bacde"
            );
            // Act & Assert
            mockMvc.perform(put("/api/user")
                            .with(jwt())
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void whenUsernameIsInvalid() throws Exception {
            // Arrange
            var request = new UpdateUserRequest(
                    "email@example.com",
                    "@@",
                    "This is a bio",
                    "https://image.com/image",
                    "12345bacde"
            );
            // Act & Assert
            mockMvc.perform(put("/api/user")
                            .with(jwt())
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void whenPasswordIsInvalid() throws Exception {
            // Arrange
            var request = new UpdateUserRequest(
                    "email@example.com",
                    "@@",
                    "This is a bio",
                    "https://image.com/image",
                    "@@"
            );
            // Act & Assert
            mockMvc.perform(put("/api/user")
                            .with(jwt())
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }


    }
}
