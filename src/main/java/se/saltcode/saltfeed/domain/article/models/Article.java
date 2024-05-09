package se.saltcode.saltfeed.domain.article.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import se.saltcode.saltfeed.domain.tag.models.Tag;
import se.saltcode.saltfeed.domain.user.models.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP(0)")
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP(0)")
    protected LocalDateTime updatedAt;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1023)
    private String description;

    @Column(nullable = false, length = 65535)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorited",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> usersWhoHaveFavorited = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tagged",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isFavoritedByUser(User user) {
        return usersWhoHaveFavorited.stream().anyMatch(foundUser -> foundUser.equals(user));
    }

    public int getFavoriteCount() {
        return usersWhoHaveFavorited.size();
    }

    public User getAuthor() {
        return author;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void addUserToFavoritedBy(User user) {
        usersWhoHaveFavorited.add(user);
    }

    public void removeUserFromFavoritedBy(User user) {
        usersWhoHaveFavorited.remove(user);
    }

    public void setTags(Set<Tag> newTags) {
        tags = newTags;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}
