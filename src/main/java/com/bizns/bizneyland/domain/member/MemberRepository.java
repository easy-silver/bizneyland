package com.bizns.bizneyland.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.id > 0 ORDER BY m.id DESC")
    List<Member> findAllDesc();

    @Query("SELECT m FROM Member m join fetch m.company ORDER BY m.id DESC")
    List<Member> findAllWithCompany();

    @Query("SELECT m FROM Member m WHERE m.userSeq = :userSeq")
    Optional<Member> findByUserSeq(@Param("userSeq") Long userSeq);

    @Query("SELECT m FROM Member m WHERE m.company.id = :companySeq")
    List<Member> findByCompanySeq(@Param("companySeq") Long companySeq);

    @Query("SELECT m FROM Member m join fetch m.company where m.id = :id")
    Optional<Member> findOneWithCompany(@Param("id") Long memberSeq);

    @Query("SELECT COUNT(m.id) FROM Member m WHERE m.company.id = :id")
    int memberCount(@Param("id") Long companySeq);

}
