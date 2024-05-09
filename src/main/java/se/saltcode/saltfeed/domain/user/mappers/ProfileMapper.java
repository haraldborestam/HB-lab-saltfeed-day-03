package se.saltcode.saltfeed.domain.user.mappers;

import se.saltcode.saltfeed.domain.user.dtos.*;
import se.saltcode.saltfeed.domain.user.models.Profile;
import se.saltcode.saltfeed.domain.user.models.User;

public class ProfileMapper {

    public static ProfileResponse mapToProfileResponse(Profile profile, User user) {
        return new ProfileResponse(mapToProfileDto(profile, user));
    }
    public static ProfileDto mapToProfileDto(Profile profile, User loggedInUser) {
        return new ProfileDto(
                profile.getUsername(),
                profile.getBio(),
                profile.getImage(),
                profile.hasFollower(loggedInUser)
        );
    }
}
