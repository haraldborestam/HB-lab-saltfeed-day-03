package se.saltcode.saltfeed.domain.greeting.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingMockFileReader implements GreetingReader {

    private final List<String> greetings;

    public GreetingMockFileReader(List<String> greetings) {
        this.greetings = greetings;
    }


    public String getPoliteGreeting() {
        return greetings.get(0);
    }

    public String getImpoliteGreeting() {
        return greetings.get(0);
    }
}
