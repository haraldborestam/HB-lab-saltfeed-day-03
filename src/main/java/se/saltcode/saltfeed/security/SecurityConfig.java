package se.saltcode.saltfeed.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final byte[] jwtKey;

    SecurityConfig(@Value("${jwt.key}") String jwtKey) {
        this.jwtKey = jwtKey.getBytes();
    }

    @Bean
    @Order(1)
    SecurityFilterChain publicSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        return http
                .securityMatcher("api/public/**", "/error")
                .cors(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    @Order(10)
    SecurityFilterChain internalSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(mvc.pattern("/api/tags")).permitAll()
                        .requestMatchers(mvc.pattern("/api/greetings")).permitAll()
                        .requestMatchers(mvc.pattern("/api/users/**")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/articles/feed")).authenticated()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/articles/**")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/profiles/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(config -> config.jwt(withDefaults()))
                .build();
    }
    @Bean
    @Order(7)
    SecurityFilterChain h2ConsoleFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(antMatcher("/h2-console/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }

    @Bean
    @Order(8)
    SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatchers(matchers -> matchers
                        .requestMatchers(
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/v3/api-docs/**"),
                                antMatcher("/proxy/**")
                        )
                )
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey));
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withSecretKey(new SecretKeySpec(jwtKey, "RSA"))
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    AuthenticationManager authManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
