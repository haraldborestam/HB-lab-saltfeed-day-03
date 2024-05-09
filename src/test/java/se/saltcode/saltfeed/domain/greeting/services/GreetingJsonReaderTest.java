package se.saltcode.saltfeed.domain.greeting.services;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GreetingJsonReaderTest {

    private static final String GREETINGS_FILE = "data/test-greetings.json";

    @Test
    void shouldBeAbleToCreateReaderFromFile() throws FileNotFoundException {
        // Act
        var fileReader = new GreetingJsonReader(GREETINGS_FILE);
        // Assert
        assertNotNull(fileReader);
    }

    @Test
    void shouldThrowWhenConstructedWithMissingFile() {
        // Arrange
        var fakePath = "fake-path/this-is-not-a-real-file.json";
        var errorMessage = "'" + fakePath + "' was not found";
        // Act
        var exception = assertThrows(FileNotFoundException.class, () -> new GreetingJsonReader(fakePath));
        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldBeAbleToReturnPoliteGreeting() throws FileNotFoundException {
        // Arrange
        var impoliteNote = "pickle";
        var politeNote = "nice";
        var fileReader = new GreetingJsonReader(GREETINGS_FILE);
        // Act
        var result = fileReader.getPoliteGreeting();
        // Assert
        assertTrue(result.contains(politeNote));
        assertFalse(result.contains(impoliteNote));
    }

    @Test
    void shouldBeAbleToReturnImpoliteGreeting() throws FileNotFoundException {
        // Arrange
        var impoliteNote = "pickle";
        var politeNote = "nice";
        var fileReader = new GreetingJsonReader(GREETINGS_FILE);
        // Act
        var result = fileReader.getImpoliteGreeting();
        // Assert
        assertTrue(result.contains(impoliteNote));
        assertFalse(result.contains(politeNote));
    }
}
