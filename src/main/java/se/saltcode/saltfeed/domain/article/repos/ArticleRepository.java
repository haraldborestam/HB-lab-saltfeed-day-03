package se.saltcode.saltfeed.domain.article.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import se.saltcode.saltfeed.domain.article.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long>, CrudRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    Optional<Article> findBySlug(String slug);
    List<Article> findByUsersWhoHaveFavorited_ProfileUsername(String username, Pageable pageable);
}
