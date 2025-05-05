package com.mdsl.onboarding.security.controllers;

import com.mdsl.onboarding.common.response.ApiResponse;
import com.mdsl.onboarding.security.dtos.TokenRequestDTO;
import com.mdsl.onboarding.security.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling authentication requests.
 * Provides an endpoint to authenticate users and return a JWT token.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth APIs")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

    /**
     * Authenticates the user based on username and password, and returns a JWT access token if successful.
     *
     * @param user an object containing the username and password credentials
     * @return ApiResponse containing a JWT token as a string if authentication is successful
     * This endpoint is public and typically used during login to obtain a JWT token.
     * The token should be included in the Authorization header (`Bearer <token>`) for future requests.
     */
    @PostMapping("/get-access-token")
    public ApiResponse<String> authenticateUser(@RequestBody TokenRequestDTO user) {
        // Authenticate the user using the provided credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        // Extract authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generate a JWT token using the username
        return ApiResponse.success(jwtUtils.generateToken(userDetails.getUsername()), true);
    }
}
