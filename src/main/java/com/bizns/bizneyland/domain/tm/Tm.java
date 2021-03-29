package com.bizns.bizneyland.domain.tm;

import com.bizns.bizneyland.domain.BaseTimeEntity;
import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Tm extends BaseTimeEntity {

    // TM 일련번호(PK)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tmSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_seq", nullable = false)
    private Client client;

    // 상담일시
    @Column(nullable = false)
    private LocalDateTime callDate = LocalDateTime.now();

    // TM 담당자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caller_seq", referencedColumnName = "member_seq", nullable = false)
    private Member caller;

    // 희망 통화 시간
    private String hopeCallTime;

    // 자금 용도
    private String purpose;

    // 대출 희망 금액
    private Integer hopeAmount;

    // 신용 상태
    private String creditStatus;

    // 주거래 은행
    private String mainBank;

    // 체납 여부
    private Character arrearsYn;

    // 대출 여부
    private Character loanYn;

    // 대출 총액
    private Integer loanAmount;

    // 주택 종류
    private String houseType;

    // 특이사항
    private String memo;

    @Builder
    public Tm(Client client, LocalDateTime callDate, Member caller, String hopeCallTime, String purpose,
              Integer hopeAmount, String creditStatus, String mainBank, Character arrearsYn,
              Character loanYn, Integer loanAmount, String houseType, String memo) {
        this.client = client;
        this.callDate = callDate;
        this.caller = caller;
        this.hopeCallTime = hopeCallTime;
        this.purpose = purpose;
        this.hopeAmount = hopeAmount;
        this.creditStatus = creditStatus;
        this.mainBank = mainBank;
        this.arrearsYn = arrearsYn;
        this.loanYn = loanYn;
        this.loanAmount = loanAmount;
        this.houseType = houseType;
        this.memo = memo;
    }
}
