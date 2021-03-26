package com.bizns.bizneyland.domain.tm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TmRepository extends JpaRepository<Tm, Long> {

    @Query("SELECT t FROM Tm t ORDER BY t.id")
    List<Tm> findAllDesc();
}
