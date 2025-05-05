package com.mdsl.onboarding.common.service;

import com.mdsl.onboarding.common.BaseEntity;
import com.mdsl.onboarding.common.enums.ErrorResponseCode;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Generic service interface providing common "Find" operations
 * for entities extending BaseEntity.
 *
 * @param <MOD> the entity/model class extending BaseEntity
 * @param <R>   the repository type which must extend JpaRepository and JpaSpecificationExecutor
 */
public interface FindService<MOD extends BaseEntity, R extends JpaRepository<MOD, Long> & JpaSpecificationExecutor<MOD>> {

    /**
     * @return the JPA repository for the model/entity
     */
    R getRepository();

    /**
     * Retrieves all entities from the database.
     *
     * @return a list of all model instances
     */
    default List<MOD> findAll() {
        return this.getRepository().findAll();
    }

    /**
     * Finds a model by its ID.
     *
     * @param id the ID of the entity
     * @return an Optional containing the found model
     * @throws CustomServiceException if the entity is not found
     */
    default Optional<MOD> findById(Long id) throws CustomServiceException {
        Optional<MOD> mod = this.getRepository().findById(id);
        if (mod.isEmpty()) {
            throw new CustomServiceException("No entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
        }
        return mod;
    }

    /**
     * Checks whether an entity exists by its ID.
     *
     * @param id the ID to check
     * @return true if the entity exists, false otherwise
     */
    default Boolean existsById(Long id) {
        return this.getRepository().existsById(id);
    }
}
