package se.saltcode.saltfeed.domain.greeting.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.saltcode.saltfeed.domain.greeting.dto.GreetingsResponse;
import se.saltcode.saltfeed.domain.greeting.services.GreetingService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/public/greetings")
public class GreetingsController {

    private final GreetingService greetingService;

    public GreetingsController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping
    public GreetingsResponse getGreeting(@RequestParam String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("You must provide a name!");
        }
        var time = LocalDateTime.now();
        var greeting = greetingService.greet(name, time);
        return new GreetingsResponse(
                name,
                time,
                greeting
        );
    }
}
