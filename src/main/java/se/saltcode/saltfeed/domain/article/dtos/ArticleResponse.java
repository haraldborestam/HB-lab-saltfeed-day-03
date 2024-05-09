package se.saltcode.saltfeed.domain.article.dtos;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("article")
public record ArticleResponse(
        ArticleDto article
){}
