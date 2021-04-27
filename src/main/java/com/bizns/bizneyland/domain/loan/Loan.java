package com.bizns.bizneyland.domain.loan;

import com.bizns.bizneyland.domain.tm.Tm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class Loan {

    /* 대출정보 번호(PK) */
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long loanSeq;

    /* TM상담 번호(FK) */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tm_seq", nullable = false)
    private Tm tm;

    /* 기관 이름 */
    @Column(nullable = false)
    private String creditor;

    /* 대출 금액 */
    @Column(nullable = false)
    private int amount;

    /* 용도 */
    private String purpose;

    /* 특이사항 */
    private String memo;

    @Builder
    public Loan(Tm tm, String creditor, int amount, String purpose, String memo) {
        this.tm = tm;
        this.creditor = creditor;
        this.amount = amount;
        this.purpose = purpose;
        this.memo = memo;
    }

    public Loan setTm(Tm tm) {
        this.tm = tm;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
