package se.saltcode.saltfeed.domain.comment.dtos;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("comment")
public record CommentResponse (
    CommentDto comment
){}
