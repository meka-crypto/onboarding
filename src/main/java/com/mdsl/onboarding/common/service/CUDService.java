package com.mdsl.onboarding.common.service;

import com.mdsl.onboarding.common.BaseEntity;
import com.mdsl.onboarding.common.enums.ErrorResponseCode;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Generic interface providing basic Create, Update, and Delete (CUD) operations
 * for any entity that extends BaseEntity.
 *
 * @param <MOD> The model/entity class that extends BaseEntity
 * @param <REQ> The DTO (Request) class for create/update operations
 * @param <R>   The JPA repository for the entity
 *
 * Implementing classes must provide model class type, repository, and a ModelMapper instance.
 */
public interface CUDService<MOD extends BaseEntity, REQ, R extends JpaRepository<MOD, Long>> {

    /**
     * @return the class type of the model/entity
     */
    Class getModelClass();

    /**
     * @return the JPA repository for the model/entity
     */
    R getRepository();

    /**
     * @return the ModelMapper instance used for mapping DTOs to entities
     */
    ModelMapper getModelMapper();

    /**
     * Creates a new entity by mapping the DTO to the entity and saving it.
     *
     * @param req the request DTO
     * @return the saved entity
     * @throws CustomServiceException if mapping or saving fails
     */
    default MOD saveNew(REQ req) throws CustomServiceException {
        MOD mod = (MOD) this.getModelMapper().map(req, this.getModelClass());
        return this.getRepository().save(mod);
    }

    /**
     * Fully updates an existing entity by ID, copying all properties from the DTO.
     *
     * @param id  the ID of the entity to update
     * @param req the request DTO
     * @return the updated entity
     * @throws CustomServiceException if the entity is not found or the ID is invalid
     */
    default MOD fullUpdate(Long id, REQ req) throws CustomServiceException {
        if (null != id && !id.toString().isEmpty()) {
            Optional<MOD> model = this.getRepository().findById(id);
            if (model.isEmpty()) {
                throw new CustomServiceException("no entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
            } else {
                MOD mod = model.get();
                BeanUtils.copyProperties(req, mod); // Copy all non-null properties from req to mod
                return this.getRepository().save(mod);
            }
        } else {
            throw new CustomServiceException("cannot be null or empty", ErrorResponseCode.NOT_NULL);
        }
    }

    /**
     * Deletes an entity by ID.
     *
     * @param id the ID of the entity to delete
     * @throws CustomServiceException if the entity is not found
     */
    default void delete(Long id) throws CustomServiceException {
        Optional<MOD> mod = this.getRepository().findById(id);
        if (mod.isEmpty()) {
            throw new CustomServiceException("no entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
        } else {
            MOD model = mod.get();
            this.getRepository().delete(model);
        }
    }

    /**
     * Partially updates an existing entity by mapping only provided fields from the DTO.
     *
     * @param id  the ID of the entity to update
     * @param req the request DTO
     * @return the updated entity
     * @throws CustomServiceException if the entity is not found or ID is invalid
     */
    default MOD patchUpdate(Long id, REQ req) throws CustomServiceException {
        if (null != id && !id.toString().isEmpty()) {
            Optional<MOD> model = this.getRepository().findById(id);
            if (model.isEmpty()) {
                throw new CustomServiceException("no entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
            } else {
                MOD mod = model.get();
                this.getModelMapper().map(req, mod); // Only map non-null fields from req to mod
                return this.getRepository().save(mod);
            }
        } else {
            throw new CustomServiceException("cannot be null or empty", ErrorResponseCode.NOT_NULL);
        }
    }
}
