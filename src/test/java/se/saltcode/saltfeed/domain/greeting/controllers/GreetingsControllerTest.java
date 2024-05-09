package se.saltcode.saltfeed.domain.greeting.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.saltcode.saltfeed.config.TestConfig;
import se.saltcode.saltfeed.domain.greeting.services.GreetingService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingsController.class)
@ContextConfiguration(classes = TestConfig.class)
class GreetingsControllerTest {
    private static final String NAME = "Adam";
    private static final String GREETING = "Hello " + NAME + "!";
    @MockBean
    private GreetingService service;
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldBeAbleToReturnGreetingWhenProvidedAName() throws Exception {
        // Arrange
        when(service.greet(anyString(), any(LocalDateTime.class))).thenReturn(GREETING);
        // Act & Assert
        mvc.perform(get("/api/public/greetings").queryParam("name", NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.greeting").value(GREETING))
                .andExpect(jsonPath("$.greetedAt", Matchers.startsWith(LocalDate.now().toString()))
                );
    }

    @Test
    void shouldRejectWhenNoNameIsProvided() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/greetings"))
                .andExpect(status().isBadRequest());
    }
}
