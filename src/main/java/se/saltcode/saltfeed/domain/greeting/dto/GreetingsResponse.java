package se.saltcode.saltfeed.domain.greeting.dto;

import java.time.LocalDateTime;

public record GreetingsResponse(
        String name,
        LocalDateTime greetedAt,
        String greeting
) {}
