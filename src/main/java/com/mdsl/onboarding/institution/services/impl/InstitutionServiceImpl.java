package com.mdsl.onboarding.institution.services.impl;

import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.institution.dtos.InstitutionRequestDTO;
import com.mdsl.onboarding.institution.models.Institution;
import com.mdsl.onboarding.institution.repositories.InstitutionRepository;
import com.mdsl.onboarding.institution.services.InstitutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final ModelMapper modelMapper;

    @Override
    public Class getModelClass() {
        return Institution.class;
    }

    @Override
    public InstitutionRepository getRepository() {
        return institutionRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public List<Institution> findInstitutionsByEnabled() {
        return institutionRepository.findInstitutionsByEnabled();
    }

    @Override
    public Institution createOrUpdate(InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException {
        Institution institution;
        if (institutionRequestDTO.getId() == null || institutionRequestDTO.getId() == 0) {
            log.debug("creating new Institution");
            institutionRequestDTO.setId(null);
            institution = saveNew(institutionRequestDTO);
        } else {
            log.debug("updating existing Institution");
            institution = patchUpdate(institutionRequestDTO.getId(), institutionRequestDTO);
        }
        return institution;
    }
}
