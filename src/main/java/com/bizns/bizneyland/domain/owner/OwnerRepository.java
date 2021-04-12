package com.bizns.bizneyland.domain.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE o.client.clientSeq = ?1")
    List<Owner> findAllByClientSeq(Long clientSeq);
}
