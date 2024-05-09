package se.saltcode.saltfeed.domain.comment.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import se.saltcode.saltfeed.domain.article.models.Article;
import se.saltcode.saltfeed.domain.user.models.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @Column(nullable = false, length = 1023)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public long getId() {
        return id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
