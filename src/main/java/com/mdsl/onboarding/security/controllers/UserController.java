package com.mdsl.onboarding.security.controllers;

import com.mdsl.onboarding.common.enums.Status;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.common.response.ApiResponse;
import com.mdsl.onboarding.security.dtos.UserRequestDTO;
import com.mdsl.onboarding.security.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for managing user-related operations.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User APIs")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    /**
     * Registers a new user.
     *
     * @param userRequestDTO a DTO containing new user information such as username and password
     * @return ApiResponse with success message if registration is successful
     * @throws CustomServiceException if an internal error occurs during registration
     * This endpoint hashes the password using BCrypt before saving
     */
    @PostMapping()
    public ApiResponse<String> registerUser(@RequestBody UserRequestDTO userRequestDTO) throws CustomServiceException {
        // Check if username is already taken
        if (userService.existsByUsername(userRequestDTO.getUsername())) {
            return ApiResponse.success("Error: Username is already taken!", true);
        }

        // Set user status and encrypt password
        userRequestDTO.setStatus(Status.ENABLED);
        userRequestDTO.setUsername(userRequestDTO.getUsername()); // This line is redundant but kept for clarity
        userRequestDTO.setPassword(encoder.encode(userRequestDTO.getPassword()));

        // Save new user
        userService.saveNew(userRequestDTO);

        // Return success response
        return ApiResponse.created("User registered successfully!", "success", true);
    }
}
