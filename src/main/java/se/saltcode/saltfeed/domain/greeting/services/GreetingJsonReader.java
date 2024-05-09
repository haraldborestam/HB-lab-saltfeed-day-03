package se.saltcode.saltfeed.domain.greeting.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

@Service
@Primary
public class GreetingJsonReader implements GreetingReader {

    private final Greetings greetings;

    public GreetingJsonReader(@Value("${greetings-files.json}") String jsonFile) throws FileNotFoundException {
        try {
            var jsonStream = getClass().getClassLoader().getResourceAsStream(jsonFile);
            greetings = new ObjectMapper().readValue(jsonStream, Greetings.class);
        } catch (IllegalArgumentException | IOException e) {
            throw new FileNotFoundException("'" + jsonFile + "' was not found");
        }
    }

    public String getPoliteGreeting() {
        return getRandomString(greetings.polite());
    }

    public String getImpoliteGreeting() {
        return getRandomString(greetings.impolite());
    }

    private String getRandomString(List<String> strings) {
        var randomIndex = current().nextInt(strings.size());
        return strings.get(randomIndex);
    }

    private record Greetings(
            List<String> polite,
            List<String> impolite
    ) {
    }
}
