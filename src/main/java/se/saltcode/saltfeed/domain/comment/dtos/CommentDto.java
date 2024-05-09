package se.saltcode.saltfeed.domain.comment.dtos;

import se.saltcode.saltfeed.domain.user.dtos.ProfileDto;

import java.time.LocalDateTime;

public record CommentDto(
        long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String body,
        ProfileDto author
) {}

