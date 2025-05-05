package com.mdsl.onboarding.insitution.services;

import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.common.service.CUDService;
import com.mdsl.onboarding.common.service.FindService;
import com.mdsl.onboarding.insitution.dtos.InstitutionRequestDTO;
import com.mdsl.onboarding.insitution.models.Institution;
import com.mdsl.onboarding.insitution.repositories.InstitutionRepository;

import java.util.List;

public interface InstitutionService extends FindService<Institution, InstitutionRepository>, CUDService<Institution, InstitutionRequestDTO, InstitutionRepository> {
    List<Institution> findInstitutionsByEnabled();
    Institution createOrUpdate(InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException;
}
