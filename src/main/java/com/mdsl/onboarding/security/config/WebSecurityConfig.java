package com.mdsl.onboarding.security.config;

import com.mdsl.onboarding.security.AuthEntryPointJwt;
import com.mdsl.onboarding.security.AuthTokenFilter;
import com.mdsl.onboarding.security.services.CustomUserDetailsService;
import com.mdsl.onboarding.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for setting up JWT-based authentication.
 * Configures how HTTP requests are secured and how authentication is handled.
 */
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtil jwtUtil;

    /**
     * Defines the JWT authentication filter to be used in the security filter chain.
     * This filter parses the JWT token from the request header and sets the authentication context.
     *
     * @return a configured AuthTokenFilter instance
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtil, userDetailsService);
    }

    /**
     * Provides an AuthenticationManager bean which is required for processing authentication.
     *
     * @param authenticationConfiguration Spring Security’s built-in authentication config
     * @return AuthenticationManager instance
     * @throws Exception if an error occurs during instantiation
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Provides a password encoder bean using BCrypt hashing algorithm.
     * Used for encoding and verifying user passwords.
     *
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the HTTP security for the application.
     * - Disables CSRF and CORS protections (suitable for APIs with JWT).
     * - Configures stateless session management (no session stored on server).
     * - Registers a custom unauthorized handler.
     * - Allows unrestricted access to authentication and Swagger documentation endpoints.
     * - Secures all other endpoints (authentication required).
     * - Adds the JWT token filter before Spring Security's default username/password filter.
     *
     * @param http the HttpSecurity object to customize
     * @return SecurityFilterChain instance that Spring Boot uses internally
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler) // Handles unauthorized access attempts
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No HTTP sessions will be created or used
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        "/auth/**",             // Public authentication endpoints
                                        "/swagger-ui.html",              // Swagger UI root
                                        "/swagger-ui/**",                // Swagger UI assets
                                        "/v3/api-docs/**",               // OpenAPI docs
                                        "/swagger-resources/**",         // Swagger resources
                                        "/webjars/**"                    // Static web resources for Swagger
                                ).permitAll()
                                .anyRequest().authenticated()       // All other endpoints require authentication
                );

        // Add the JWT token filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
