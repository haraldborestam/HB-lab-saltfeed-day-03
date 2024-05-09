package se.saltcode.saltfeed.domain.greeting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GreetingServiceImpl implements GreetingService {

    private final GreetingReader reader;

    @Autowired
    public GreetingServiceImpl(GreetingReader reader) {
        this.reader = reader;
    }

    @Override
    public String greet(String name, LocalDateTime time) {
        return time.getSecond() % 2 == 0
                ? greetPolitely(name)
                : greetImpolitely(name);

    }

    @Override
    public String greetImpolitely(String name) {
        var greeting = reader.getImpoliteGreeting();
        return greeting.replace("{name}", name);
    }

    @Override
    public String greetPolitely(String name) {
        var greeting = reader.getPoliteGreeting();
        return greeting.replace("{name}", name);
    }
}
