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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth Apis")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

    @PostMapping("/get-access-token")
    public ApiResponse<String> authenticateUser(@RequestBody TokenRequestDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ApiResponse.success(jwtUtils.generateToken(userDetails.getUsername()), true);
    }


}