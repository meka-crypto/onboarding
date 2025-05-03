package com.mdsl.onboarding.insitution.repository;

import com.mdsl.onboarding.insitution.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {
    @Query("SELECT Ins from Institution Ins where status = 1")
    List<Institution> findInstitutionsByEnabled();
}
