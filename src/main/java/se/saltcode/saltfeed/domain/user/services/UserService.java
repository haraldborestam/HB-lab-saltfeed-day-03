package se.saltcode.saltfeed.domain.user.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.saltcode.saltfeed.domain.user.dtos.CreateUserRequest;
import se.saltcode.saltfeed.domain.user.dtos.LoginRequest;
import se.saltcode.saltfeed.domain.user.dtos.UpdateUserRequest;
import se.saltcode.saltfeed.domain.user.models.User;
import se.saltcode.saltfeed.domain.user.repositories.UserRepository;
import se.saltcode.saltfeed.security.JwtTokenService;
import se.saltcode.saltfeed.security.SaltUserDetails;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository repo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    public UserService(UserRepository repo, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.repo = repo;
        this.authenticationManager = authenticationManager;
        this.tokenService = jwtTokenService;
    }

    public User getUserByUsername(String username) {
        return repo.findByProfileUsernameIgnoreCase(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public User getUserByEmail(String username) {
        return repo.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public User getUser(Jwt jwt) {
        if (jwt == null) {
            return new User();
        }
        return getUserByEmail(jwt.getSubject());
    }

    @Transactional(readOnly = true)
    public User login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        var details = (SaltUserDetails) authentication.getPrincipal();
        return details.getUserEntity();
    }

    public User createUser(CreateUserRequest request) {
        repo.findAllByProfileUsernameIgnoreCaseOrEmailIgnoreCase(request.username(), request.email())
                .stream()
                .findAny()
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User already exists");
                });
        var newUser = new User();
        newUser.getProfile().setUsername(request.username());
        newUser.setEmail(request.email());
        newUser.setPassword("{noop}" + request.password());
        return repo.save(newUser);
    }

    @Transactional
    public User updateUser(UpdateUserRequest request, User user) {
        var profile = user.getProfile();
        if (request.username() != null) {
            repo.findByProfileUsernameIgnoreCase(request.username())
                    .filter(found -> !found.equals(user))
                    .ifPresent(found -> {
                        throw new IllegalArgumentException("Username already exists");
                    });
            profile.setUsername(request.username());
        }
        if (request.email() != null) {
            repo.findByEmailIgnoreCase(request.email())
                    .filter(found -> !found.equals(user))
                    .ifPresent(found -> {
                        throw new IllegalArgumentException("Email already exists");
                    });
            user.setEmail(request.email());
        }
        if (request.password() != null) {
            user.setPassword(request.password());
        }
        if (request.bio() != null) {
            profile.setBio(request.bio());
        }
        if (request.image() != null) {
            profile.setImage(request.image());
        }
        return repo.save(user);
    }
}
