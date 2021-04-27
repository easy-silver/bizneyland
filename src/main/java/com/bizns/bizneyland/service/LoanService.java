package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.loan.Loan;
import com.bizns.bizneyland.domain.loan.LoanRepository;
import com.bizns.bizneyland.domain.tm.Tm;
import com.bizns.bizneyland.domain.tm.TmRepository;
import com.bizns.bizneyland.web.dto.LoanResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoanService {

    private final LoanRepository repository;
    private final TmRepository tmRepository;

    /**
     * 복수 건 등록
     *
     * @param loans
     */
    public void save(List<Loan> loans) {
        for (Loan loan : loans) {
            repository.save(loan);
        }
    }

    /**
     * 상담 건에 해당하는 대출 정보 목록
     * @param tmSeq
     * @return
     */
    public List<LoanResponseDto> findAllByTm(Long tmSeq) {
        Tm tm = tmRepository.findById(tmSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상담 건입니다. SEQ=" + tmSeq));

        return repository.findByTm(tm)
                .stream()
                .map(LoanResponseDto::new)
                .collect(Collectors.toList());
    }

}
