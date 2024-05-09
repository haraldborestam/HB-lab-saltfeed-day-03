package se.saltcode.saltfeed.domain.greeting.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Greeting service")
class GreetingServiceImplTest {

    private static final GreetingFileReader fileReader = mock(GreetingFileReader.class);
    private static final GreetingService service = new GreetingServiceImpl(fileReader);
    private static final String NAME = "Adam";
    private static final String POLITE_GREETING = "Nice to meet you {name}!";
    private static final String IMPOLITE_GREETING = "{name}! You look like a pickle";

    @BeforeAll
    static void setup() {
        when(fileReader.getPoliteGreeting()).thenReturn(POLITE_GREETING);
        when(fileReader.getImpoliteGreeting()).thenReturn(IMPOLITE_GREETING);
    }

    @Nested
    class shouldReturnPoliteGreeting {
        @Test
        void WhenAskedForPoliteGreeting() {
            // Arrange
            var expectedMessage = POLITE_GREETING.replace("{name}", NAME);
            // Act
            var result = service.greetPolitely(NAME);
            // Assert
            assertEquals(expectedMessage, result);
        }

        @Test
        void WhenAskedForGreetingOnEvenSeconds() {
            // Arrange
            var expectedMessage = POLITE_GREETING.replace("{name}", NAME);
            var time = LocalDateTime.now().withSecond(2);
            // Act
            var result = service.greet(NAME, time);
            // Assert
            assertEquals(expectedMessage, result);
        }

    }

    @Nested
    class shouldReturnImpoliteGreeting {
        @Test
        void WhenAskedForImpoliteGreeting() {
            // Arrange
            var expectedMessage = IMPOLITE_GREETING.replace("{name}", NAME);
            // Act
            var result = service.greetImpolitely(NAME);
            // Assert
            assertEquals(expectedMessage, result);
        }

        @Test
        void WhenAskedForGreetingOnOddSeconds() {
            // Arrange
            var expectedMessage = IMPOLITE_GREETING.replace("{name}", NAME);
            var time = LocalDateTime.now().withSecond(3);
            // Act
            var result = service.greet(NAME, time);
            // Assert
            assertEquals(expectedMessage, result);
        }

    }
}
