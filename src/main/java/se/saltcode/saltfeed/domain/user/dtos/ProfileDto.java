package se.saltcode.saltfeed.domain.user.dtos;

public record ProfileDto(
        String username,
        String bio,
        String image,
        boolean following
) {}
