package se.saltcode.saltfeed.domain.comment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.saltcode.saltfeed.domain.article.services.ArticleService;
import se.saltcode.saltfeed.domain.comment.dtos.CreateCommentRequest;
import se.saltcode.saltfeed.domain.comment.models.Comment;
import se.saltcode.saltfeed.domain.comment.repos.CommentRepository;
import se.saltcode.saltfeed.domain.user.models.User;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    @Autowired
    public CommentService(CommentRepository commentRepository, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
    }

    public Comment addComment(String slug, CreateCommentRequest request, User author) {
        var article = articleService.getArticle(slug);
        var comment = new Comment();
        comment.setBody(request.body());
        comment.setAuthor(author);
        comment.setArticle(article);

        return commentRepository.save(comment);
    }

    public List<Comment> getComments(String slug) {
        var article = articleService.getArticle(slug);
        return commentRepository.findByArticleId(article.getId());
    }

    public void deleteComment(String slug, long id) {
        commentRepository.delete(getComment(slug, id));
    }

    private Comment getComment(String slug, long id) {
        return getComments(slug).stream().filter(comment -> comment.getId() == id).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
    }
}

