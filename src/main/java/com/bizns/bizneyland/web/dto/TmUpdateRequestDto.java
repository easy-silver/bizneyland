package com.bizns.bizneyland.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class TmUpdateRequestDto {

    private Long tmSeq;
    private String recipient;
    private String headcount;
    private Integer hopeAmount;
    private Character arrearsYn;
    private String arrearsDetail;
    private String creditStatus;
    private String hopeCallTime;
    private String memo;

    @Builder
    public TmUpdateRequestDto(Long tmSeq, String recipient, String headcount, Integer hopeAmount,
                              Character arrearsYn, String arrearsDetail, String creditStatus,
                              String hopeCallTime, String memo) {
        this.tmSeq = tmSeq;
        this.recipient = recipient;
        this.headcount = headcount;
        this.hopeAmount = hopeAmount;
        this.arrearsYn = arrearsYn;
        this.arrearsDetail = arrearsDetail;
        this.creditStatus = creditStatus;
        this.hopeCallTime = hopeCallTime;
        this.memo = memo;
    }
}
