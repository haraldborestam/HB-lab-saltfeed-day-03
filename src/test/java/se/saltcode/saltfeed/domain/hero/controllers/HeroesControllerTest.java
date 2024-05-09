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

        // Ett HeroResponse kräver i sin constructor en Hero och en tid.
        // Vi har ingen färdig Hero att skicka så vi skapar en istället.
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

    // Det här testet kolla om ett simulerat GET request till VÅR server
    // returnerar en http status: 200 OK.
    @Test
    void shouldReturnListOfHeroesOnRequest() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes"))
                .andExpect(status().isOk());

    }

    // Det här testet kollar både status 200 OK samt om responsen är innehållsmässigt korrekt.
    //
    @Test
    void shouldReturnMockedListOnRequest() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", Matchers.is(mockHeroListResponse.count())));
    }

    // Det här testet simulerar en inkommande GET request till vår server.
    // Det innebär att den Controller som är ansvarig för den aktuella endpointen kommer köras.
    // Därför kollar vi i HeroesController-classen.
    // Vi förväntar oss en http status: 200 OK, från vår egna server.
    // OBS: vi bryr oss alltså inte om vad servern skickar oss, vi bryr oss bara om
    // att den skickar något överhuvudtaget.
    @Test
    void shouldReturnHeroOnRequest() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes/123"))
                .andExpect(status().isOk());
    }

    // Till skillnad från föregående test så är det här lite mer utvecklat.
    // Här vill vi nämligen också ha ett väldigt specifikt svar från servern.
    // Vi vill nämligen att servern returnerar:
    // namn: Captain Kirk
    // url: https://trekkiapi.dev/api/people/3232/
    //
    @Test
    void shouldReturnMockedHeroData() throws Exception {
        // Act & Assert
        mvc.perform(get("/api/public/heroes/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hero.name", Matchers.is(mockHeroResponse.hero().getName())));
    }
}