package se.saltcode.saltfeed.domain.article.dtos;

import com.fasterxml.jackson.annotation.JsonTypeName;
import se.saltcode.saltfeed.domain.tag.dtos.TagsResponse;
import se.saltcode.saltfeed.domain.user.dtos.ProfileDto;

import java.time.LocalDateTime;
import java.util.List;

@JsonTypeName("article")
public record ArticleDto(
        String slug,
        String title,
        String description,
        String body,
        List<String> tagList,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean favorited,
        int favoritesCount,
        ProfileDto author
){}
