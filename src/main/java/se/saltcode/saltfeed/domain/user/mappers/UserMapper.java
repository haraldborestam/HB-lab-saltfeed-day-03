package se.saltcode.saltfeed.domain.user.mappers;

import se.saltcode.saltfeed.domain.user.dtos.*;
import se.saltcode.saltfeed.domain.user.models.Profile;
import se.saltcode.saltfeed.domain.user.models.User;

import java.util.UUID;

public class UserMapper {

    public static UserResponse mapToUserResponse(User user, String token) {
        return new UserResponse(mapToUserDto(user, token));
    }

    public static UserDto mapToUserDto(User user, String token) {
        return new UserDto(
                user.getEmail(),
                token,
                user.getProfile().getUsername(),
                user.getProfile().getBio(),
                user.getProfile().getImage()
        );
    }
}
