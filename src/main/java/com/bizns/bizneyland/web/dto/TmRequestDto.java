package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.tm.Tm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class TmRequestDto {

    private Long clientSeq;
    private Long userSeq;
    private LocalDateTime callDate;
    private String recipient;
    private String headcount;
    private Integer hopeAmount;
    private Character arrearsYn;
    private String arrearsDetail;
    private String creditStatus;
    private String hopeCallTime;
    private String memo;

    @Builder
    public TmRequestDto(Long clientSeq, Long userSeq, LocalDateTime callDate,
                        String recipient, String headcount, Integer hopeAmount,
                        Character arrearsYn, String arrearsDetail, String creditStatus,
                        String hopeCallTime, String memo) {
        this.clientSeq = clientSeq;
        this.userSeq = userSeq;
        this.callDate = callDate;
        this.recipient = recipient;
        this.headcount = headcount;
        this.hopeAmount = hopeAmount;
        this.arrearsYn = arrearsYn;
        this.arrearsDetail = arrearsDetail;
        this.creditStatus = creditStatus;
        this.hopeCallTime = hopeCallTime;
        this.memo = memo;
    }

    public Tm toEntity() {
        return Tm.builder()
                .callDate(callDate)
                .recipient(recipient)
                .headcount(headcount)
                .hopeAmount(hopeAmount)
                .arrearsYn(arrearsYn)
                .arrearsDetail(arrearsDetail)
                .creditStatus(creditStatus)
                .hopeCallTime(hopeCallTime)
                .memo(memo)
                .build();
    }
}
