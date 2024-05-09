package se.saltcode.saltfeed.domain.article.mappers;

import se.saltcode.saltfeed.domain.article.dtos.*;
import se.saltcode.saltfeed.domain.article.models.Article;
import se.saltcode.saltfeed.domain.tag.dtos.TagsResponse;
import se.saltcode.saltfeed.domain.tag.mappers.TagMapper;
import se.saltcode.saltfeed.domain.tag.models.Tag;
import se.saltcode.saltfeed.domain.user.mappers.ProfileMapper;
import se.saltcode.saltfeed.domain.user.models.User;

import java.util.List;

public class ArticleMapper {

    public static ArticleResponse mapToArticleResponse(Article article, User user) {
        return new ArticleResponse(mapToArticleDto(article, user)
        );
    }

    public static ArticleDto mapToArticleDto(Article article, User user) {
        return new ArticleDto(
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                article.getTags().stream().map(tag -> tag.getName()).toList(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.isFavoritedByUser(user),
                article.getFavoriteCount(),
                ProfileMapper.mapToProfileDto(article.getAuthor().getProfile(), user)
        );
    }

    public static ArticlesResponse mapToArticlesResponse(List<Article> articles, User user) {
        return new ArticlesResponse(
                articles.stream().map(article -> mapToArticleDto(article, user)).toList(),
                articles.size()
        );
    }

    public static TagsResponse mapToTagsResponse(List<Tag> tags) {
        return new TagsResponse(tags.stream().map(Tag::getName).toList());
    }
}
