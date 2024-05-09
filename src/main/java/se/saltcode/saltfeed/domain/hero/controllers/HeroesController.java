package se.saltcode.saltfeed.domain.hero.controllers;

import jakarta.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.saltcode.saltfeed.domain.hero.dtos.HeroResponse;
import se.saltcode.saltfeed.domain.hero.dtos.HeroListResponse;
import se.saltcode.saltfeed.domain.hero.models.Hero;
import se.saltcode.saltfeed.domain.hero.services.SwapiClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/public/heroes")
public class HeroesController {

    private final SwapiClient service;

    @Autowired
    public HeroesController(SwapiClient service) {
        this.service = service;
    }

    @GetMapping
    public HeroListResponse listHeroes() {
        List<Hero> hjältelistan = new ArrayList<>();
        hjältelistan.add(new Hero("Captain Kirk", "https://trekkiapi.dev/api/people/3232/"));
        hjältelistan.add(new Hero("Kosh Naranek", "https://trekkiapi.dev/api/people/1123/"));
        System.out.println("------------- INUTI Controllern\n\t INUTI Metoden listHeroes\n\t\t name: " + hjältelistan.get(0).getName());
        return new HeroListResponse(hjältelistan, LocalDateTime.now(), hjältelistan.size());
    }

    // Den här körs när GET requesten vill nå endpoint "api/public/heroes/id"
    // Den här ska returnera ett HeroResponse-objekt
    @GetMapping("{id}")
    public HeroResponse getHero(@PathVariable int id) {
        System.out.println("\n\nHEJ HEJ HEJ NU KÖRS getHero\n\n");
        //throw new NotSupportedException("We have not implemented this yet");
        return new HeroResponse(new Hero("Captain Kirk", "https://trekkiapi.dev/api/people/3232/"), LocalDateTime.now());
    }
}
