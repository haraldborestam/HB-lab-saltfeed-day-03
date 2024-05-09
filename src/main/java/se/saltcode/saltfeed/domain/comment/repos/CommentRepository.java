package se.saltcode.saltfeed.domain.comment.repos;

import org.springframework.data.repository.CrudRepository;
import se.saltcode.saltfeed.domain.comment.models.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByArticleId(long id);
}
