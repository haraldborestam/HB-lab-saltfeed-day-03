package se.saltcode.saltfeed.domain.user.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Parent;

import java.util.HashSet;
import java.util.Set;

@Embeddable
public class Profile {

    @Parent
    @OneToOne
    private User user;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(length = 1024)
    private String bio = "";

    @Column
    private String image = "";

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id", referencedColumnName = "id"))
    private Set<User> followers = new HashSet<>();

    public boolean hasFollower(User user) {
        return followers.stream().anyMatch(foundUser -> foundUser.equals(user));
    }

    public void addFollower(User user) {
        if (this.equals(user.getProfile())) {
            return;
        }
        followers.add(user);
    }

    public void removeFollower(User user) {
        followers.remove(user);
    }

    public int totalFollowers() {
        return followers.size();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imageUrl) {
        this.image = imageUrl;
    }
}
