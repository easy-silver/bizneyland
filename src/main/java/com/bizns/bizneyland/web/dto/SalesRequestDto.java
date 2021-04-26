package com.bizns.bizneyland.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SalesRequestDto {
    private Long clientSeq;
    private Integer[] salesAmount;
    private String[] salesYears;

    @Builder
    public SalesRequestDto(Long clientSeq, Integer[] salesAmount, String[] salesYears) {
        this.clientSeq = clientSeq;
        this.salesAmount = salesAmount;
        this.salesYears = salesYears;
    }
}
