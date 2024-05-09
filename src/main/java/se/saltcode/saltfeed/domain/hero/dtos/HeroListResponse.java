package se.saltcode.saltfeed.domain.hero.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.saltcode.saltfeed.domain.hero.models.Hero;

import java.time.LocalDateTime;
import java.util.List;

// Den här record classen har följande fields:
// List<Hero> vilket är en collections-lista innehållandes Hero-objekt
// LocalDateTime vilket är tiden då requesten kom in.
// count vilket gissningsvis ska motsvara antalet items i listan.
public record HeroListResponse(
        @JsonProperty("results")
        List<Hero> heroes,
        LocalDateTime requestedAt,
        int count
) {
}
