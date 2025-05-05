package com.mdsl.onboarding.institution.services;

import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.common.service.CUDService;
import com.mdsl.onboarding.common.service.FindService;
import com.mdsl.onboarding.institution.dtos.InstitutionRequestDTO;
import com.mdsl.onboarding.institution.models.Institution;
import com.mdsl.onboarding.institution.repositories.InstitutionRepository;

import java.util.List;

public interface InstitutionService extends FindService<Institution, InstitutionRepository>, CUDService<Institution, InstitutionRequestDTO, InstitutionRepository> {
    List<Institution> findInstitutionsByEnabled();
    Institution createOrUpdate(InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException;
}
