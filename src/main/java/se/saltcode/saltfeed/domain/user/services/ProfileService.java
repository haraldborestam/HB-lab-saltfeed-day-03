package se.saltcode.saltfeed.domain.user.services;

import org.springframework.stereotype.Service;
import se.saltcode.saltfeed.domain.user.models.Profile;
import se.saltcode.saltfeed.domain.user.models.User;
import se.saltcode.saltfeed.domain.user.repositories.UserRepository;

@Service
public class ProfileService {

    private final UserService userService;
    private final UserRepository userRepository;

    public ProfileService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Profile getProfile(String username) {
        return userService.getUserByUsername(username).getProfile();
    }


    public Profile followProfile(String username, User user) {
        var profile = getProfile(username);
        profile.addFollower(user);
        userRepository.save(profile.getUser());
        return profile;
    }

    public Profile unfollowProfile(String username, User user) {
        var profile = getProfile(username);
        profile.removeFollower(user);
        userRepository.save(profile.getUser());
        return profile;
    }
}
