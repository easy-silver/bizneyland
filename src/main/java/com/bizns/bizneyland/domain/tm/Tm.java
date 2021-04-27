package com.bizns.bizneyland.domain.tm;

import com.bizns.bizneyland.domain.BaseTimeEntity;
import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
public class Tm extends BaseTimeEntity {

    /* TM 일련번호(PK) */
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long tmSeq;

    /* 고객(업체) 번호(FK) */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_seq", nullable = false)
    private Client client;

    /* TM 담당자(FK) */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "caller_seq", referencedColumnName = "member_seq", nullable = false)
    private Member caller;

    // 상담일시
    @Column(nullable = false)
    private LocalDateTime callDate = LocalDateTime.now();

    // 수신인
    private String recipient;
    // 직원 수
    private String headcount;
    // 대출 희망 금액
    private Integer hopeAmount;
    // 체납 여부
    private Character arrearsYn;
    // 체납 상세 내역
    private String arrearsDetail;
    // 신용 상태
    private String creditStatus;
    // 희망 통화 시간
    private String hopeCallTime;
    // 메모
    private String memo;

    @Builder
    public Tm(Client client, Member caller, LocalDateTime callDate, String recipient,
              String headcount, Integer hopeAmount, Character arrearsYn, String arrearsDetail,
              String creditStatus, String hopeCallTime, String memo) {
        this.client = client;
        this.caller = caller;
        this.callDate = callDate == null ? LocalDateTime.now() : callDate;
        this.recipient = recipient;
        this.headcount = headcount;
        this.hopeAmount = hopeAmount;
        this.arrearsYn = arrearsYn;
        this.arrearsDetail = arrearsDetail;
        this.creditStatus = creditStatus;
        this.hopeCallTime = hopeCallTime;
        this.memo = memo;
    }

    public Tm updateClient(Client client) {
        this.client = client;
        return this;
    }

    public Tm updateCaller(Member member) {
        this.caller = member;
        return this;
    }
}
