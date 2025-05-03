package com.mdsl.onboarding.common.service;

import com.mdsl.onboarding.common.BaseEntity;
import com.mdsl.onboarding.common.enums.ErrorResponseCode;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface FindService<MOD extends BaseEntity, R extends JpaRepository<MOD, Long> & JpaSpecificationExecutor<MOD>> {
    R getRepository();

    default List<MOD> findAll() {
        return this.getRepository().findAll();
    }

    default Optional<MOD> findById(Long id) throws CustomServiceException{
        Optional<MOD> mod = this.getRepository().findById(id);
        if (mod.isEmpty()) {
            throw new CustomServiceException("no entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
        }
        return mod;
    }

    default Boolean existsById(Long id) {
        return this.getRepository().existsById(id);
    }
}