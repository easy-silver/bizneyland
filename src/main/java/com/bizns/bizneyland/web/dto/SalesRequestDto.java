package com.bizns.bizneyland.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SalesRequestDto {
    private Long clientSeq;
    private String salesYear;
    private Integer salesAmount;

    @Builder
    public SalesRequestDto(Long clientSeq, Integer salesAmount, String salesYear) {
        this.clientSeq = clientSeq;
        this.salesAmount = salesAmount;
        this.salesYear = salesYear;
    }
}
