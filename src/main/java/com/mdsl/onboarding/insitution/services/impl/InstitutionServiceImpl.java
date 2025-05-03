package com.mdsl.onboarding.insitution.services.impl;

import com.mdsl.onboarding.insitution.models.Institution;
import com.mdsl.onboarding.insitution.repositories.InstitutionRepository;
import com.mdsl.onboarding.insitution.services.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
}
