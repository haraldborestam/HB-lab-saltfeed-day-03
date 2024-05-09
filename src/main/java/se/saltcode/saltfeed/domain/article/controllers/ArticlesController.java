package se.saltcode.saltfeed.domain.article.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.saltcode.saltfeed.domain.article.dtos.*;
import se.saltcode.saltfeed.domain.article.mappers.ArticleMapper;
import se.saltcode.saltfeed.domain.article.queries.ArticleQuery;
import se.saltcode.saltfeed.domain.article.queries.FeedQuery;
import se.saltcode.saltfeed.domain.article.services.ArticleService;
import se.saltcode.saltfeed.domain.user.services.UserService;

@RestController
@RequestMapping("/api/articles")
@SecurityRequirement(name = "Bearer Authentication")
public class ArticlesController {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticlesController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public ArticlesResponse listArticles(@ModelAttribute ArticleQuery query, @AuthenticationPrincipal Jwt jwt) {
        var loggedInUser = userService.getUser(jwt);
        var articles = articleService.listArticles(query);

        return ArticleMapper.mapToArticlesResponse(articles, loggedInUser);
    }

    @PostMapping
    public ArticleResponse createArticle(
            @Valid @RequestBody CreateArticleRequest request,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        var article = articleService.createArticle(request, loggedInUser);

        return ArticleMapper.mapToArticleResponse(article, loggedInUser);
    }

    @GetMapping("/feed")
    public ArticlesResponse listFeed(
            @ModelAttribute FeedQuery query,
            @AuthenticationPrincipal(expression = "subject") String email)
    {
        var loggedInUser = userService.getUserByEmail(email);
        var articles = articleService.listFeed(query, loggedInUser);

        return ArticleMapper.mapToArticlesResponse(articles, loggedInUser);
    }

    @GetMapping("/{slug}")
    public ArticleResponse getArticle(@PathVariable String slug, @AuthenticationPrincipal Jwt jwt) {
        var loggedInUser = userService.getUser(jwt);
        var article = articleService.getArticle(slug);

        return ArticleMapper.mapToArticleResponse(article, loggedInUser);
    }

    @PutMapping("/{slug}")
    public ArticleResponse updateArticle(
            @PathVariable String slug,
            @Valid @RequestBody UpdateArticleRequest request,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        var article = articleService.updateArticle(slug, request);

        return ArticleMapper.mapToArticleResponse(article, loggedInUser);
    }

    @DeleteMapping("/{slug}")
    public void deleteArticle(@PathVariable String slug) {

        articleService.deleteArticle(slug);
    }

    @PostMapping("/{slug}/favorite")
    @SecurityRequirement(name = "Bearer Authentication")
    public ArticleResponse favoriteArticle(
            @PathVariable String slug,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        var article = articleService.favoriteArticle(slug, loggedInUser);

        return ArticleMapper.mapToArticleResponse(article, loggedInUser);
    }

    @DeleteMapping("/{slug}/favorite")
    @SecurityRequirement(name = "Bearer Authentication")
    public ArticleResponse unfavoriteArticle(
            @PathVariable String slug,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        var article = articleService.unfavoriteArticle(slug, loggedInUser);

        return ArticleMapper.mapToArticleResponse(article, loggedInUser);
    }
}
