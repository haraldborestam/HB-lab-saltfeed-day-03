package se.saltcode.saltfeed.domain.user.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileTest {

    private List<User> users;

    @BeforeEach
    void setup() {
        users = Arrays.asList(
                new User(),
                new User(),
                new User(),
                new User(),
                new User()
        );
    }

    @Test
    void shouldAddUserAsFollower() {
        // Arrange
        var testProfile = users.get(0).getProfile();
        var secondUser = users.get(1);
        var initialFollowerCount = testProfile.totalFollowers();
        // Act
        testProfile.addFollower(secondUser);
        // Assert
        assertTrue(testProfile.hasFollower(secondUser));
        assertTrue(testProfile.totalFollowers() == initialFollowerCount + 1);
    }

    @Test
    void shouldNotAddSelfAsFollower() {
        // Arrange
        var self = users.get(0);
        var testProfile = self.getProfile();
        var initialFollowerCount = testProfile.totalFollowers();
        // Act
        testProfile.addFollower(self);
        // Assert
        assertFalse(testProfile.hasFollower(self));
        assertTrue(testProfile.totalFollowers() == initialFollowerCount);
    }

    @Test
    void shouldNotAddUserAgainWhenAlreadyAFollower() {
        // Arrange
        var testProfile = users.get(0).getProfile();
        var secondUser = users.get(1);
        testProfile.addFollower(secondUser);
        var initialFollowerCount = testProfile.totalFollowers();
        // Act
        testProfile.addFollower(secondUser);
        // Assert
        assertTrue(testProfile.hasFollower(secondUser));
        assertTrue(testProfile.totalFollowers() == initialFollowerCount);
    }

    @Test
    void shouldAddMultipleUsersAsFollower() {
        // Arrange
        var testProfile = users.get(0).getProfile();
        var initialFollowerCount = testProfile.totalFollowers();
        // Act
        users.stream().skip(1)
                .forEach(testProfile::addFollower);
        // Assert
        assertTrue(testProfile.totalFollowers() == initialFollowerCount + users.size() - 1);
    }
}
