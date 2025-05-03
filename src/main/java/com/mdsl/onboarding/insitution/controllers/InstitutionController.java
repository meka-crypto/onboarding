package com.mdsl.onboarding.insitution.controllers;

import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.common.response.ApiResponse;
import com.mdsl.onboarding.common.utils.CommonUtils;
import com.mdsl.onboarding.insitution.dtos.InstitutionRequestDTO;
import com.mdsl.onboarding.insitution.dtos.InstitutionResponseDTO;
import com.mdsl.onboarding.insitution.models.Institution;
import com.mdsl.onboarding.insitution.services.InstitutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/institution")
@Tag(name = "Institution", description = "Institution Apis")
@Slf4j
public class InstitutionController {

    private final InstitutionService institutionService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public ApiResponse<InstitutionResponseDTO> createOrUpdate(@RequestBody InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException {
        Institution institution;
        if (institutionRequestDTO.getId() == null || institutionRequestDTO.getId() == 0) {
            log.debug("creating new Institution");
            institutionRequestDTO.setId(null);
            institution = institutionService.saveNew(institutionRequestDTO);
        } else {
            log.debug("updating existing Institution");
            institution = institutionService.patchUpdate(institutionRequestDTO.getId(), institutionRequestDTO);
        }
        return ApiResponse.success(modelMapper.map(institution, InstitutionResponseDTO.class), true);
    }

    @GetMapping("/{id}")
    public ApiResponse<InstitutionResponseDTO> getById(@PathVariable("id") Long id) throws CustomServiceException {
        return ApiResponse.success(modelMapper.map(institutionService.findById(id), InstitutionResponseDTO.class), true);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteById(@PathVariable("id") Long id) throws CustomServiceException {
        institutionService.delete(id);
        log.debug("Institution with id {} deleted", id);
        return ApiResponse.success(true);
    }

    @GetMapping()
    public ApiResponse<List<InstitutionResponseDTO>> getAll() {
        return ApiResponse.success(CommonUtils.mapList(institutionService.findAll(), InstitutionResponseDTO.class), true);
    }

    @GetMapping("/get-active")
    public ApiResponse<List<InstitutionResponseDTO>> getActive() {
        return ApiResponse.success(CommonUtils.mapList(institutionService.findInstitutionsByEnabled(), InstitutionResponseDTO.class), true);
    }
}