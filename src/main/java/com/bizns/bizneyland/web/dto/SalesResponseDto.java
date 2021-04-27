package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.sales.Sales;
import lombok.Getter;

@Getter
public class SalesResponseDto {
    private Long salesSeq;
    private Long clientSeq;
    private String salesYear;
    private String salesAmount;

    public SalesResponseDto(Sales entity) {
        this.salesSeq = entity.getSalesSeq();
        this.clientSeq = entity.getClient().getClientSeq();
        this.salesYear = entity.getSalesYear();
        this.salesAmount = entity.getAmount();
    }
}
