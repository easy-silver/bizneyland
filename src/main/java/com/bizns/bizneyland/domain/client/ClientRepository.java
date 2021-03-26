package com.bizns.bizneyland.domain.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c ORDER BY c.id DESC")
    List<Client> findAllDesc();
}
