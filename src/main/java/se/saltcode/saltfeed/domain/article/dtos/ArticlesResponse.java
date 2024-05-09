package se.saltcode.saltfeed.domain.article.dtos;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("articles")
public record ArticlesResponse(
        List<ArticleDto> articles,
        int articlesCount
) {}
