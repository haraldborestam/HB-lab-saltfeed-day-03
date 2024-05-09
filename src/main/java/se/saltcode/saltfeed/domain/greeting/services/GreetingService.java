package se.saltcode.saltfeed.domain.greeting.services;

import java.time.LocalDateTime;

public interface GreetingService {
    String greet(String name, LocalDateTime time);

    String greetImpolitely(String name);

    String greetPolitely(String name);
}
