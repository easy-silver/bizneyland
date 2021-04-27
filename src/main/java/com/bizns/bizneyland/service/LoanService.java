package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.loan.Loan;
import com.bizns.bizneyland.domain.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoanService {

    private final LoanRepository repository;

    /**
     * 복수 건 등록
     * @param loans
     */
    public void save(List<Loan> loans) {
        for (Loan loan : loans) {
            repository.save(loan);
        }
    }
}
