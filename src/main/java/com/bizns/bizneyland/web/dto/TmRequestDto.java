package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.tm.Tm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TmRequestDto {

    private Long clientSeq;
    private Long userSeq;
    private String hopeCallTime;
    private Integer hopeAmount;
    private String creditStatus;
    private Character arrearsYn;
    private String memo;

    @Builder
    public TmRequestDto(Long clientSeq, Long userSeq, String hopeCallTime, Integer hopeAmount,
                        String creditStatus, Character arrearsYn, String memo) {
        this.clientSeq = clientSeq;
        this.userSeq = userSeq;
        this.hopeCallTime = hopeCallTime;
        this.hopeAmount = hopeAmount;
        this.creditStatus = creditStatus;
        this.arrearsYn = arrearsYn;
        this.memo = memo;
    }

    public Tm toEntity() {
        return Tm.builder()
                .hopeCallTime(hopeCallTime)
                .hopeAmount(hopeAmount)
                .creditStatus(creditStatus)
                .arrearsYn(arrearsYn)
                .memo(memo)
                .build();
    }
}
