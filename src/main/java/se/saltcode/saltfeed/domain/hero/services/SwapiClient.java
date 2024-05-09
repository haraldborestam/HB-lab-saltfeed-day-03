package se.saltcode.saltfeed.domain.hero.services;

import jakarta.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import se.saltcode.saltfeed.domain.hero.dtos.HeroListResponse;
import se.saltcode.saltfeed.domain.hero.dtos.HeroResponse;

@Service
public class SwapiClient {

    private final WebClient client;

    public SwapiClient(@Value("${swapi-url}") String baseUrl) {
        client = WebClient.create(baseUrl);
    }

    public HeroResponse getHero(int id) throws NotSupportedException{
        throw new NotSupportedException("We need to be able to get one hero from Swapi");
    }

    public HeroListResponse getHeroes() throws NotSupportedException{
        throw new NotSupportedException("And also a whole list");
    }
}
