package com.mdsl.onboarding.common.service;

import com.mdsl.onboarding.common.BaseEntity;
import com.mdsl.onboarding.common.enums.ErrorResponseCode;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CUDService<MOD extends BaseEntity, REQ, R extends JpaRepository<MOD, Long>> {
    Class getModelClass();

    R getRepository();

    ModelMapper getModelMapper();

    default MOD saveNew(REQ req) throws CustomServiceException {
        MOD mod = (MOD) this.getModelMapper().map(req, this.getModelClass());
        return this.getRepository().save(mod);
    }

    default MOD fullUpdate(Long id, REQ req) throws CustomServiceException {
        if (null != id && !id.toString().isEmpty()) {
            Optional<MOD> model = this.getRepository().findById(id);
            if (model.isEmpty()) {
                throw new CustomServiceException("no entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
            } else {
                MOD mod = model.get();
                BeanUtils.copyProperties(req, mod);
                return this.getRepository().save(mod);
            }
        } else {
            throw new CustomServiceException("cannot be null or empty", ErrorResponseCode.NOT_NULL);
        }
    }

    default void delete(Long id) throws CustomServiceException {
        Optional<MOD> mod = this.getRepository().findById(id);
        if (mod.isEmpty()) {
            throw new CustomServiceException("no entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
        } else {
            MOD model = mod.get();
            this.getRepository().delete(model);
        }
    }

    default MOD patchUpdate(Long id, REQ req) throws CustomServiceException {
        if (null != id && !id.toString().isEmpty()) {
            Optional<MOD> model = this.getRepository().findById(id);
            if (model.isEmpty()) {
                throw new CustomServiceException("no entity found with id = " + id, ErrorResponseCode.NOT_FOUND);
            } else {
                MOD mod = model.get();
                this.getModelMapper().map(req, mod);
                return this.getRepository().save(mod);
            }
        } else {
            throw new CustomServiceException("cannot be null or empty", ErrorResponseCode.NOT_NULL);
        }
    }
}