package se.saltcode.saltfeed.domain.article.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeName("article")
public record UpdateArticleRequest(
        String title,
        String description,
        String body
) {
}
