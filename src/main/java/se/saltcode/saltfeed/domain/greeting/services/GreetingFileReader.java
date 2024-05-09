package se.saltcode.saltfeed.domain.greeting.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

@Service
public class GreetingFileReader implements GreetingReader {

    private final List<String> politeGreetings;
    private final List<String> impoliteGreetings;

    public GreetingFileReader(
            @Value("${greetings-files.polite}") String politeGreetingsFile,
            @Value("${greetings-files.impolite}") String impoliteGreetingsFile) throws FileNotFoundException {
        politeGreetings = getAllLines(politeGreetingsFile);
        impoliteGreetings = getAllLines(impoliteGreetingsFile);
    }

    public String getPoliteGreeting() {
        return getRandomString(politeGreetings);
    }

    public String getImpoliteGreeting() {
        return getRandomString(impoliteGreetings);
    }

    private List<String> getAllLines(String fileName) throws FileNotFoundException {
        try {
            var path = getClass().getClassLoader().getResource(fileName);
            return Files.readAllLines(Paths.get(path.toURI()));
        } catch (NullPointerException | IOException | URISyntaxException e) {
            throw new FileNotFoundException("'" + fileName + "' was not found");
        }
    }

    private String getRandomString(List<String> strings) {
        var randomIndex = current().nextInt(strings.size());
        return strings.get(randomIndex);
    }
}
