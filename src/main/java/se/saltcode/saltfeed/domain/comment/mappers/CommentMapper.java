package se.saltcode.saltfeed.domain.comment.mappers;

import se.saltcode.saltfeed.domain.comment.dtos.CommentResponse;
import se.saltcode.saltfeed.domain.comment.dtos.CommentsResponse;
import se.saltcode.saltfeed.domain.comment.dtos.CommentDto;
import se.saltcode.saltfeed.domain.comment.models.Comment;
import se.saltcode.saltfeed.domain.user.mappers.ProfileMapper;
import se.saltcode.saltfeed.domain.user.models.User;

import java.util.List;

public class CommentMapper {
    public static CommentResponse mapToCommentResponse(Comment comment, User user) {
        return new CommentResponse(mapToCommentDto(comment, user));
    }

    public static CommentDto mapToCommentDto(Comment comment, User user) {
        return new CommentDto(
                comment.getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getBody(),
                ProfileMapper.mapToProfileDto(comment.getAuthor().getProfile(), user)
        );
    }

    public static CommentsResponse mapToCommentListDto(List<Comment> comments, User user) {
        return new CommentsResponse(
                comments.stream().map(comment -> mapToCommentDto(comment, user)).toList()
        );
    }
}
