package com.mdsl.onboarding.insitution.controllers;

import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.common.response.ApiResponse;
import com.mdsl.onboarding.common.utils.CommonUtils;
import com.mdsl.onboarding.insitution.dtos.InstitutionRequestDTO;
import com.mdsl.onboarding.insitution.dtos.InstitutionResponseDTO;
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
@Tag(name = "Institution", description = "Institution APIs")
@Slf4j
public class InstitutionController {

    private final InstitutionService institutionService;
    private final ModelMapper modelMapper;

    /**
     * Create or update an institution.
     *
     * This API handles both creation of a new institution and update of an existing one.
     * The differentiation is usually done based on whether the request contains an ID.
     *
     * @param institutionRequestDTO the data of the institution to be created or updated
     * @return ApiResponse containing the created/updated InstitutionResponseDTO
     * @throws CustomServiceException if validation fails or processing error occurs
     */
    @PostMapping()
    public ApiResponse<InstitutionResponseDTO> createOrUpdate(@RequestBody InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException {
        return ApiResponse.success(modelMapper.map(institutionService.createOrUpdate(institutionRequestDTO), InstitutionResponseDTO.class), true);
    }

    /**
     * Get institution by its ID.
     *
     * This API retrieves the details of a single institution based on the given ID.
     *
     * @param id the unique identifier of the institution
     * @return ApiResponse containing the InstitutionResponseDTO
     * @throws CustomServiceException if the institution is not found or an error occurs
     */
    @GetMapping("/{id}")
    public ApiResponse<InstitutionResponseDTO> getById(@PathVariable("id") Long id) throws CustomServiceException {
        return ApiResponse.success(modelMapper.map(institutionService.findById(id), InstitutionResponseDTO.class), true);
    }

    /**
     * Delete institution by ID.
     *
     * This API deletes an institution based on the given ID.
     * It performs a hard delete from the data source.
     *
     * @param id the unique identifier of the institution to be deleted
     * @return ApiResponse indicating success or failure
     * @throws CustomServiceException if the institution is not found or deletion fails
     */
    @DeleteMapping("/{id}")
    public ApiResponse deleteById(@PathVariable("id") Long id) throws CustomServiceException {
        institutionService.delete(id);
        log.debug("Institution with id {} deleted", id);
        return ApiResponse.success(true);
    }

    /**
     * Get all institutions.
     *
     * This API retrieves all institutions in the system, regardless of their active status.
     *
     * @return ApiResponse containing a list of InstitutionResponseDTOs
     */
    @GetMapping()
    public ApiResponse<List<InstitutionResponseDTO>> getAll() {
        return ApiResponse.success(CommonUtils.mapList(institutionService.findAll(), InstitutionResponseDTO.class), true);
    }

    /**
     * Get all active institutions.
     *
     * This API returns only the institutions that are currently marked as ENABLED with status 1.
     *
     * @return ApiResponse containing a list of active InstitutionResponseDTOs
     */
    @GetMapping("/get-active")
    public ApiResponse<List<InstitutionResponseDTO>> getActive() {
        return ApiResponse.success(CommonUtils.mapList(institutionService.findInstitutionsByEnabled(), InstitutionResponseDTO.class), true);
    }
}