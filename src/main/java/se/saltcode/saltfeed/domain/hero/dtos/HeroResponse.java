package se.saltcode.saltfeed.domain.hero.dtos;

import se.saltcode.saltfeed.domain.hero.models.Hero;

import java.time.LocalDateTime;

public record HeroResponse(
        Hero hero,
        LocalDateTime requestedAt
) {
}
