package se.saltcode.saltfeed.domain.hero.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.saltcode.saltfeed.domain.hero.models.Hero;

import java.time.LocalDateTime;
import java.util.List;

public record HeroListResponse(
        @JsonProperty("results")
        List<Hero> heroes,
        LocalDateTime requestedAt,
        int count
) {
}
