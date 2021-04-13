package com.bizns.bizneyland.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m ORDER BY m.id DESC")
    List<Member> findAllDesc();

    @Query("SELECT m FROM Member m WHERE m.userSeq = ?1")
    Member findByUserSeq(Long userSeq);

    @Query("SELECT m FROM Member m WHERE m.company.id = ?1")
    List<Member> findByCompanySeq(Long companySeq);
}
