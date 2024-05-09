package se.saltcode.saltfeed.domain.comment.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.saltcode.saltfeed.domain.comment.dtos.CreateCommentRequest;
import se.saltcode.saltfeed.domain.comment.dtos.CommentResponse;
import se.saltcode.saltfeed.domain.comment.dtos.CommentsResponse;
import se.saltcode.saltfeed.domain.comment.mappers.CommentMapper;
import se.saltcode.saltfeed.domain.comment.services.CommentService;
import se.saltcode.saltfeed.domain.user.services.UserService;

@RestController
@RequestMapping("/api/articles/{slug}/comments")
@SecurityRequirement(name = "Bearer Authentication")
public class CommentsController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentsController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping
    public CommentsResponse getComments(@PathVariable String slug, @AuthenticationPrincipal Jwt jwt) {
        var loggedInUser = userService.getUser(jwt);
        var comments = commentService.getComments(slug);

        return CommentMapper.mapToCommentListDto(comments, loggedInUser);
    }

    @PostMapping
    public CommentResponse addComment(
            @PathVariable String slug,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal(expression = "subject") String email
    ) {
        var loggedInUser = userService.getUserByEmail(email);
        var comments = commentService.addComment(slug, request, loggedInUser);

        return CommentMapper.mapToCommentResponse(comments, loggedInUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long id, @PathVariable String slug) {
        commentService.deleteComment(slug,id);
    }

}
