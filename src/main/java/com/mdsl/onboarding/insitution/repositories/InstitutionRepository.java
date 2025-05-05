package com.mdsl.onboarding.insitution.repositories;

import com.mdsl.onboarding.insitution.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing Institution entities.
 * <p>
 * Extends JpaRepository for basic CRUD operations and JpaSpecificationExecutor for dynamic query support.
 */
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {

    /**
     * Fetches all institutions that are marked as ENABLED.
     * Enabled institution is one where the `status` field has a value of 1.
     *
     * @return a list of active (enabled) Institution entities
     */
    @Query("SELECT ins FROM Institution ins WHERE ins.status = 1")
    List<Institution> findInstitutionsByEnabled();
}