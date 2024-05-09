package se.saltcode.saltfeed.domain.hero.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.saltcode.saltfeed.config.TestConfig;
import se.saltcode.saltfeed.domain.hero.dtos.HeroListResponse;
import se.saltcode.saltfeed.domain.hero.dtos.HeroResponse;
import se.saltcode.saltfeed.domain.hero.models.Hero;
import se.saltcode.saltfeed.domain.hero.services.SwapiClient;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HeroesController.class)
@ContextConfiguration(classes = TestConfig.class)
class HeroesControllerTest {

    @MockBean
    private SwapiClient apiServiceMock;

    @Autowired
    private MockMvc mvc;
    private HeroResponse mockHeroResponse;
    private HeroListResponse mockHeroListResponse;
    private HeroResponse mockHero;

    @BeforeEach
    void setup() throws Exception {

        mockHeroResponse = new HeroResponse(
                new Hero("Captain Kirk", "https://trekkiapi.dev/api/people/3232/"),
                LocalDateTime.now()
        );

        mockHeroListResponse = new HeroListResponse(
                Arrays.asList(
                        new Hero("Captain Kirk", "https://trekkiapi.dev/api/people/3232/"),
                        new Hero("Kosh Naranek", "https://trekkiapi.dev/api/people/1123/")
                ),
                LocalDateTime.now(),
                143
        );
        
        when(apiServiceMock.getHero(anyInt())).thenReturn(mockHeroResponse);
        when(apiServiceMock.getHeroes()).thenReturn(mockHeroListResponse);
    }

    @Test
    void shouldReturnListOfHeroesOnRequest() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldReturnMockedListonRequest() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", Matchers.is(mockHeroListResponse.count())));
    }

    @Test
    void shouldReturnHeroOnRequest() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes/123"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnMockedHeroData() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hero.name", Matchers.is(mockHeroResponse.hero().getName())));
    }
}