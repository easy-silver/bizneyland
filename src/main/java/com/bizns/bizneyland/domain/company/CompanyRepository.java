package com.bizns.bizneyland.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.id > 0 ORDER BY c.id DESC")
    List<Company> findAllDesc();

    Company findByBusinessNo(String businessNo);

    @Query("SELECT COUNT(c.id) > 0 FROM Company c WHERE c.id = :seq AND c.businessNo like :businessNo%")
    boolean checkValidCompany(@Param("seq") Long companySeq, @Param("businessNo") String businessNo);
}