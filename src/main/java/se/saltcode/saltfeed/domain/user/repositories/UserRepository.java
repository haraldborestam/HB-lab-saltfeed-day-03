package se.saltcode.saltfeed.domain.user.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.saltcode.saltfeed.domain.user.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAllByProfileUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByProfileUsernameIgnoreCase(String username);

}
