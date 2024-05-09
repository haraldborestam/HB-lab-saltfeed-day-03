package se.saltcode.saltfeed.domain.greeting.services;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GreetingFileReaderTest {

    private static final String POLITE_GREETINGS_FILE = "data/polite-test-greetings.txt";
    private static final String IMPOLITE_GREETINGS_FILE = "data/impolite-test-greetings.txt";

    @Test
    void shouldBeAbleToCreateReaderWhenFilesExist() throws FileNotFoundException {
        // Act
        var fileReader = new GreetingFileReader(POLITE_GREETINGS_FILE, IMPOLITE_GREETINGS_FILE);
        // Assert
        assertNotNull(fileReader);
    }

    @Test
    void shouldThrowWhenConstructedWithMissingFiles() {
        // Arrange
        var fakePath = "fake-path/this-is-not-a-real-file.txt";
        var errorMessage = "'" + fakePath + "' was not found";
        // Act
        var exception = assertThrows(FileNotFoundException.class, () -> new GreetingFileReader(fakePath, fakePath));
        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldBeAbleToReturnPoliteGreeting() throws FileNotFoundException {
        // Arrange
        var impoliteNote = "pickle";
        var politeNote = "nice";
        var fileReader = new GreetingFileReader(POLITE_GREETINGS_FILE, IMPOLITE_GREETINGS_FILE);
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
        var fileReader = new GreetingFileReader(POLITE_GREETINGS_FILE, IMPOLITE_GREETINGS_FILE);
        // Act
        var result = fileReader.getImpoliteGreeting();
        // Assert
        assertTrue(result.contains(impoliteNote));
        assertFalse(result.contains(politeNote));
    }
}
