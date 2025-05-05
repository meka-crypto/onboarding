package com.mdsl.onboarding;

import com.mdsl.onboarding.common.enums.Status;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.institution.dtos.InstitutionRequestDTO;
import com.mdsl.onboarding.institution.models.Institution;
import com.mdsl.onboarding.institution.repositories.InstitutionRepository;
import com.mdsl.onboarding.institution.services.impl.InstitutionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OnboardingApplicationTests {

	private InstitutionRepository institutionRepository;
	private ModelMapper modelMapper;
	private InstitutionServiceImpl institutionService;

	@BeforeEach
	void setUp() {
		institutionRepository = mock(InstitutionRepository.class);
		modelMapper = new ModelMapper();
		institutionService = new InstitutionServiceImpl(institutionRepository, modelMapper);
	}

	@Test
	void shouldCreateNewInstitution_whenIdIsNull() throws CustomServiceException {
		InstitutionRequestDTO request = new InstitutionRequestDTO();
		request.setName("ABC Institution");
		request.setCode(12345L);
		request.setStatus(Status.ENABLED);

		Institution savedInstitution = new Institution();
		savedInstitution.setId(1L);
		savedInstitution.setName("ABC Institution");
		savedInstitution.setCode(12345L);
		savedInstitution.setStatus(Status.ENABLED);

		when(institutionRepository.save(any(Institution.class))).thenReturn(savedInstitution);

		Institution result = institutionService.createOrUpdate(request);

		assertThat(result.getName()).isEqualTo("ABC Institution");
		assertThat(result.getId()).isNotNull();
		verify(institutionRepository, times(1)).save(any(Institution.class));
	}

	@Test
	void shouldUpdateInstitution_whenIdIsProvided() throws CustomServiceException {
		InstitutionRequestDTO request = new InstitutionRequestDTO();
		request.setId(1L);
		request.setName("Updated Name");
		request.setCode(9999L);
		request.setStatus(Status.ENABLED);

		Institution existing = new Institution();
		existing.setId(1L);
		existing.setName("Old Name");
		existing.setCode(1111L);
		existing.setStatus(Status.ENABLED);

		Institution updated = new Institution();
		updated.setId(1L);
		updated.setName("Updated Name");
		updated.setCode(9999L);
		updated.setStatus(Status.ENABLED);

		when(institutionRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(institutionRepository.save(any(Institution.class))).thenReturn(updated);

		Institution result = institutionService.createOrUpdate(request);

		assertThat(result.getName()).isEqualTo("Updated Name");
		verify(institutionRepository).save(any(Institution.class));
	}

	@Test
	void shouldThrowException_whenUpdateIdNotFound() {
		InstitutionRequestDTO request = new InstitutionRequestDTO();
		request.setId(100L);
		request.setName("Non-existent");

		when(institutionRepository.findById(100L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> institutionService.createOrUpdate(request))
				.isInstanceOf(CustomServiceException.class)
				.hasMessageContaining("no entity found with id = " + request.getId());

		verify(institutionRepository, never()).save(any());
	}

	@Test
	void shouldReturnEnabledInstitutions() {
		Institution inst1 = new Institution();
		inst1.setId(1L);
		inst1.setStatus(Status.ENABLED);
		Institution inst2 = new Institution();
		inst2.setId(2L);
		inst2.setStatus(Status.ENABLED);

		when(institutionRepository.findInstitutionsByEnabled()).thenReturn(List.of(inst1, inst2));

		List<Institution> result = institutionService.findInstitutionsByEnabled();

		assertThat(result).hasSize(2);
		assertThat(result).extracting("status").containsOnly(Status.ENABLED);
	}

	@Test
	void testFindById_success() throws CustomServiceException {
		Institution institution = new Institution();
		institution.setId(1L);
		institution.setName("Test Institution");

		when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

		Optional<Institution> result = institutionService.findById(1L);

		assertTrue(result.isPresent());
		assertEquals(1L, result.get().getId());
		assertEquals("Test Institution", result.get().getName());
		verify(institutionRepository, times(1)).findById(1L);
	}

	@Test
	void testFindAll_success() {
		// Given
		Institution i1 = new Institution();
		i1.setId(1L);
		i1.setName("Inst A");

		Institution i2 = new Institution();
		i2.setId(2L);
		i2.setName("Inst B");

		List<Institution> institutions = Arrays.asList(i1, i2);
		when(institutionRepository.findAll()).thenReturn(institutions);

		List<Institution> result = institutionService.findAll();

		assertEquals(2, result.size());
		verify(institutionRepository, times(1)).findAll();
	}

	@Test
	void testFindById_notFound_shouldThrowCustomServiceException() {
		// Given
		Long invalidId = 999L;
		when(institutionRepository.findById(invalidId)).thenReturn(Optional.empty());

		// When & Then
		CustomServiceException exception = assertThrows(CustomServiceException.class, () -> {
			institutionService.findById(invalidId);
		});

		assertEquals("No entity found with id = " + invalidId, exception.getMessage());
		verify(institutionRepository, times(1)).findById(invalidId);
	}

}
