package se.saltcode.saltfeed.domain.user.models;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private UUID globalId = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Profile profile = new Profile();

    public long getId() {
        return id;
    }

    public UUID getGlobalId() {
        return globalId;
    }

    public void setGlobalId(UUID newId) {
        this.globalId = newId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public int hashCode() {
        return globalId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(globalId, user.globalId);
    }
}
