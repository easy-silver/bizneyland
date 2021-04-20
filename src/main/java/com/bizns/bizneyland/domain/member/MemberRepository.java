package com.bizns.bizneyland.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.id > 0 ORDER BY m.id DESC")
    List<Member> findAllDesc();

    @Query("SELECT m FROM Member m WHERE m.userSeq = ?1")
    Optional<Member> findByUserSeq(Long userSeq);

    @Query("SELECT m FROM Member m WHERE m.company.id = ?1")
    List<Member> findByCompanySeq(Long companySeq);
}
