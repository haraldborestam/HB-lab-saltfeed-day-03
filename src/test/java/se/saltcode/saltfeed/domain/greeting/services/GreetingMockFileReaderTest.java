package se.saltcode.saltfeed.domain.greeting.services;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreetingMockFileReaderTest {
    private static final List<String> GREETINGS = Arrays.asList(
            "Hello {name}, nice to meet you!",
            "You are looking nice today {name}",
            "{name}, you make my day much nicer!"
    );

    @Test
    void shouldBeAbleToReturnPoliteGreeting() throws FileNotFoundException {
        // Arrange
        var fileReader = new GreetingMockFileReader(GREETINGS);
        // Act
        var result = fileReader.getPoliteGreeting();
        // Assert
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void shouldBeAbleToReturnImpoliteGreeting() throws FileNotFoundException {
        // Arrange
        var fileReader = new GreetingMockFileReader(GREETINGS);
        // Act
        var result = fileReader.getImpoliteGreeting();
        // Assert
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
}
