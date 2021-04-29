package com.bizns.bizneyland.domain.loan;

import com.bizns.bizneyland.domain.tm.Tm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByTm(Tm tm);

    void deleteByTm(Tm tm);
}
