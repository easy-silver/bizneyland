package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.loan.Loan;
import lombok.Getter;

@Getter
public class LoanResponseDto {
    private Long loanSeq;
    private String creditor;
    private int amount;
    private String purpose;
    private String memo;

    public LoanResponseDto(Loan entity) {
        this.loanSeq = entity.getLoanSeq();
        this.creditor = entity.getCreditor();
        this.amount = entity.getAmount();
        this.purpose = entity.getPurpose();
        this.memo = entity.getMemo();
    }
}
