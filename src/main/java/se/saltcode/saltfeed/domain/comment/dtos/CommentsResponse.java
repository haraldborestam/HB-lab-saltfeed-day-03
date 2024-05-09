package se.saltcode.saltfeed.domain.comment.dtos;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("comments")
public record CommentsResponse(
        List<CommentDto> comments
){}
