package com.mdsl.onboarding.security.repositories;

import com.mdsl.onboarding.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing User entities.
 * <p>
 * Extends JpaRepository for basic CRUD operations and JpaSpecificationExecutor for dynamic query support.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Retrieves a user by their username.
     *
     * @param username the unique username of the user
     * @return an Optional containing the User if found, or empty if not
     *
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the specified username already exists.
     *
     * @param username the username to check for existence
     * @return true if a user with the given username exists, false otherwise
     *
     */
    boolean existsByUsername(String username);
}
