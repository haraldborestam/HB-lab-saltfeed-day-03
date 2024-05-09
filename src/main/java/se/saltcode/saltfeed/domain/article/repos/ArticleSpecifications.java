package se.saltcode.saltfeed.domain.article.repos;

import org.springframework.data.jpa.domain.Specification;
import se.saltcode.saltfeed.domain.article.models.Article;

import java.util.Locale;

public final class ArticleSpecifications  {
    public static Specification<Article> isUserFavorited(String username) {
        return (root, query, cb) -> {
            if (username == null || username.isBlank()) {
                return null;
            }

            return cb.equal(
                    root.join("usersWhoHaveFavorited").get("profile").get("username"),
                    username
            );
        };
    }

    public static Specification<Article> hasTag(String tagName) {
        return (root, query, cb) -> {
            if (tagName == null || tagName.isBlank()) {
                return null;
            }

            return cb.equal(
                    cb.lower(root.join("tags").get("name")),
                    tagName.toLowerCase(Locale.ROOT)
            );
        };
    }

    public static Specification<Article> hasAuthor(String authorName) {
        return (root, query, cb) -> {
            if (authorName == null || authorName.isBlank()) {
                return null;
            }

            return cb.equal(
                    root.get("author").get("profile").get("username"),
                    authorName
            );
        };
    }
}
