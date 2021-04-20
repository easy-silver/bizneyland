package com.bizns.bizneyland.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.id > 0 ORDER BY c.id DESC")
    List<Company> findAllDesc();

    Company findByBusinessNo(String businessNo);
}