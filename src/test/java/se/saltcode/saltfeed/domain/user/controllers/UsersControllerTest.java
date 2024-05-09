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
import se.saltcode.saltfeed.domain.user.dtos.CreateUserRequest;
import se.saltcode.saltfeed.domain.user.dtos.LoginRequest;
import se.saltcode.saltfeed.domain.user.dtos.UserResponse;
import se.saltcode.saltfeed.domain.user.models.User;
import se.saltcode.saltfeed.domain.user.services.UserService;
import se.saltcode.saltfeed.security.JwtTokenService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@ContextConfiguration(classes = TestConfig.class)
class UsersControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenService tokenService;
    @MockBean
    private UserService userService;

    @Test
    void shouldReturnCreatedAndUserWhenRegistring() throws Exception {
        // Arrange
        var request = new CreateUserRequest(
                "adam",
                "adam@example.com",
                "abcde12345"
        );
        when(userService.createUser(eq(request)))
                .thenReturn(new User());
        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user", Matchers.notNullValue(UserResponse.class)));
    }

    @Test
    void shouldReturnOkAndUserWhenLoggingIn() throws Exception {
        // Arrange
        var request = new LoginRequest(
                "adam@example.com",
                "abcde12345"
        );
        when(userService.login(eq(request)))
                .thenReturn(new User());
        // Act & Assert
        mockMvc.perform(post("/api/users/login")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user", Matchers.notNullValue(UserResponse.class)));
    }

    @Nested
    class shouldReturnUnprocessableContentWhenRegistring {
        @Test
        void whenPasswordIsInvalid() throws Exception {
            // Arrange
            var request = new CreateUserRequest(
                    "adam",
                    "adam@example.com",
                    "@@"
            );
            // Act & Assert
            mockMvc.perform(post("/api/users")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void whenUsernameIsInvalid() throws Exception {
            // Arrange
            var request = new CreateUserRequest(
                    "@@",
                    "adam@example.com",
                    "abcde12345"
            );
            // Act & Assert
            mockMvc.perform(post("/api/users")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void whenEmailIsInvalid() throws Exception {
            // Arrange
            var request = new CreateUserRequest(
                    "adam",
                    "adam",
                    "abcde12345"
            );
            // Act & Assert
            mockMvc.perform(post("/api/users")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void whenEmailAlreadyExsists() throws Exception {
            // Arrange
            var request = new CreateUserRequest(
                    "adam",
                    "adam@alreadyhere.com",
                    "abcde12345"
            );
            when(userService.createUser(eq(request)))
                    .thenThrow(new IllegalArgumentException("Email already exists"));
            // Act & Assert
            mockMvc.perform(post("/api/users")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void whenUsernameAlreadyExsists() throws Exception {
            // Arrange
            var request = new CreateUserRequest(
                    "adamIsTaken",
                    "adam@example.com",
                    "abcde12345"
            );
            when(userService.createUser(eq(request)))
                    .thenThrow(new IllegalArgumentException("Username already exists"));
            // Act & Assert
            mockMvc.perform(post("/api/users")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Nested
    class shouldReturnUnprocessableContentWhenLoggingIn {
        @Test
        void whenUsernameIsInvalid() throws Exception {
            // Arrange
            var request = new LoginRequest(
                    "@",
                    "abcde12345"
            );
            // Act & Assert
            mockMvc.perform(post("/api/users/login")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void whenPasswordIsInvalid() throws Exception {
            // Arrange
            var request = new LoginRequest(
                    "adam@exaple.com",
                    "@@"
            );
            // Act & Assert
            mockMvc.perform(post("/api/users/login")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }
    }
}
