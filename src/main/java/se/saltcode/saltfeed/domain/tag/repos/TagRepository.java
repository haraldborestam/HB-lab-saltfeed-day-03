package se.saltcode.saltfeed.domain.tag.repos;

import org.springframework.data.repository.ListCrudRepository;
import se.saltcode.saltfeed.domain.tag.models.Tag;

import java.util.Optional;

public interface TagRepository extends ListCrudRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
}
