package se.saltcode.saltfeed.domain.article.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.saltcode.saltfeed.domain.article.dtos.CreateArticleRequest;
import se.saltcode.saltfeed.domain.article.dtos.UpdateArticleRequest;
import se.saltcode.saltfeed.domain.article.models.Article;
import se.saltcode.saltfeed.domain.article.queries.ArticleQuery;
import se.saltcode.saltfeed.domain.article.queries.FeedQuery;
import se.saltcode.saltfeed.domain.article.repos.ArticleRepository;
import se.saltcode.saltfeed.domain.article.repos.ArticleSpecifications;
import se.saltcode.saltfeed.domain.tag.models.Tag;
import se.saltcode.saltfeed.domain.tag.services.TagService;
import se.saltcode.saltfeed.domain.user.models.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository repo;
    private final TagService tagService;

    @Autowired
    public ArticleService(
            ArticleRepository repo,
            TagService tagService
            ) {
        this.repo = repo;
        this.tagService = tagService;
    }
    public Article createArticle(CreateArticleRequest request, User author) {
        String slug = String.join("-", request.title().split(" "));

        Set<Tag> tags = request.tags().stream()
                .map(tagService::getOrCreateTag)
                .collect(Collectors.toSet());

        var article = new Article();
        article.setSlug(slug);
        article.setTitle(request.title());
        article.setDescription(request.description());
        article.setBody(request.body());
        article.setAuthor(author);
        article.setTags(tags);

        return repo.save(article);
    }

    public Article updateArticle(String slug, UpdateArticleRequest request) {
        var article = getArticle(slug);

        if (request.title() != null) {
            String newSlug = String.join("-", request.title().split(" "));
            article.setTitle(request.title());
            article.setSlug(newSlug);
        }

        if (request.description() != null) {
            article.setDescription(request.description());
        }

        if (request.body() != null) {
            article.setBody(request.body());
        }

        return repo.save(article);
    }

    public void deleteArticle(String slug) {
        Article found = repo.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Article not found"));

        repo.delete(found);
    }

    public List<Article> listArticles(ArticleQuery query) {
        return repo.findAll(
                ArticleSpecifications.hasTag(query.tag())
                        .and(ArticleSpecifications.hasAuthor(query.author()))
                        .and(ArticleSpecifications.isUserFavorited(query.favorited())),
                getPagable(query.limit(), query.offset())
        ).getContent();
    }

    public Article favoriteArticle(String slug, User user) {
        var article = getArticle(slug);
        article.addUserToFavoritedBy(user);

        return repo.save(article);
    }

    public Article unfavoriteArticle(String slug, User user) {
        Article article = getArticle(slug);
        article.removeUserFromFavoritedBy(user);

        return repo.save(article);
    }

    public Article getArticle(String slug) {
        return repo.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Article not found"));
    }

    public List<Article> listFeed(FeedQuery query, User user) {
        return repo.findAll(
                ArticleSpecifications.hasTag(query.tag())
                        .and(ArticleSpecifications.isUserFavorited(user.getProfile().getUsername())),
                getPagable(query.limit(), query.offset())
        ).getContent();
    }

    private Pageable getPagable(Integer limit, Integer offset) {
        limit = limit != null ? limit : 200;
        offset = offset != null ? offset : 0;
        int size = Math.max(5, Math.min(200, limit));
        int page = Math.max(0, offset);
        return PageRequest.of(page, size);
    }
}
