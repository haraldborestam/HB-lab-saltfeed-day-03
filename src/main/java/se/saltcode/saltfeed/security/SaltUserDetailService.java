package se.saltcode.saltfeed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.saltcode.saltfeed.domain.user.repositories.UserRepository;

@Service
public class SaltUserDetailService implements UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public SaltUserDetailService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        return repo.findByProfileUsernameIgnoreCase(usernameOrEmail)
                .or(() -> repo.findByEmailIgnoreCase(usernameOrEmail))
                .map(SaltUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));
    }
}
