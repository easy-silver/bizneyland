package com.bizns.bizneyland.domain.tm;

import com.bizns.bizneyland.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TmRepository extends JpaRepository<Tm, Long> {

    @Query("SELECT t FROM Tm t JOIN FETCH t.client JOIN FETCH t.caller ORDER BY t.tmSeq DESC")
    List<Tm> findAllDesc();

    @Query("SELECT t FROM Tm t " +
            "JOIN FETCH t.client " +
            "JOIN FETCH t.caller " +
            "WHERE t.companyCharge = :companySeq " +
            "ORDER BY t.tmSeq DESC")
    List<Tm> findByCompany(@Param("companySeq") Long companySeq);

    List<Tm> findByCaller(Member member);

    @Query("SELECT t FROM Tm t WHERE t.client.clientSeq = ?1 ORDER BY t.tmSeq DESC")
    List<Tm> findByClient(Long clientSeq);
}