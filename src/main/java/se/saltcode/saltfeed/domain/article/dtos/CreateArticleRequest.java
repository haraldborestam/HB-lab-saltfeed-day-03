package se.saltcode.saltfeed.domain.article.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeName("article")
public record CreateArticleRequest(
        String title,
        String description,
        String body,
        List<String> tags

) {}
