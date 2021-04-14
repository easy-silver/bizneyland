package com.bizns.bizneyland.domain.tm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TmRepository extends JpaRepository<Tm, Long> {

    @Query("SELECT t FROM Tm t ORDER BY t.tmSeq DESC")
    List<Tm> findAllDesc();

    @Query("SELECT t FROM Tm t WHERE t.client.clientSeq = ?1 ORDER BY t.tmSeq DESC")
    List<Tm> findByClient(Long clientSeq);
}