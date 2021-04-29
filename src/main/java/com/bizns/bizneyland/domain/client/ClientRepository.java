package com.bizns.bizneyland.domain.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c ORDER BY c.clientSeq DESC")
    List<Client> findAllDesc();

    @Query("SELECT c FROM Client c WHERE c.companyCharge = :companySeq ORDER BY c.clientSeq DESC")
    List<Client> findByCompany(@Param("companySeq") Long companySeq);

    Client findByContactAndCompanyCharge(String contact, Long companySeq);

    Client findByName(String name);
}
