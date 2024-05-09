package se.saltcode.saltfeed.domain.hero.controllers;

import jakarta.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.saltcode.saltfeed.domain.hero.dtos.HeroResponse;
import se.saltcode.saltfeed.domain.hero.dtos.HeroListResponse;
import se.saltcode.saltfeed.domain.hero.services.SwapiClient;

@RestController
@RequestMapping("api/public/heroes")
public class HeroesController {

    private final SwapiClient service;

    @Autowired
    public HeroesController(SwapiClient service) {
        this.service = service;
    }

    @GetMapping
    public HeroListResponse listHeroes() throws NotSupportedException {
        throw new NotSupportedException("We have not implemented this yet");
    }

    @GetMapping("{id}")
    public HeroResponse getHero(@PathVariable int id) throws NotSupportedException {
        throw new NotSupportedException("We have not implemented this yet");
    }
}
