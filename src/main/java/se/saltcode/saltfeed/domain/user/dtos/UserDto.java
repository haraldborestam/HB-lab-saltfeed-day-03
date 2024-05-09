package se.saltcode.saltfeed.domain.user.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

public record UserDto(
        String email,
        String token,
        String username,
        String bio,
        String image
) {}
