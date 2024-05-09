package se.saltcode.saltfeed.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import se.saltcode.saltfeed.security.SecurityConfig;

@TestConfiguration
@Import(SecurityConfig.class)
public class TestConfig {

    @MockBean
    AuthenticationManager authenticationManager;
}
