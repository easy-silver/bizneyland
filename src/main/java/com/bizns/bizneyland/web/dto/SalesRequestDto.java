package com.bizns.bizneyland.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SalesRequestDto {
    private Long clientSeq;
    private String salesYear;
    private String salesAmount;

    @Builder
    public SalesRequestDto(Long clientSeq, String salesYear, String salesAmount) {
        this.clientSeq = clientSeq;
        this.salesYear = salesYear;
        this.salesAmount = salesAmount;
    }
}
