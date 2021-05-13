package com.bizns.bizneyland.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class LoanRequestDto {

    private String[] creditors;
    private String[] amounts;
    private String[] purposes;
    private String[] memos;

    @Builder
    public LoanRequestDto(String[] creditors, String[] amounts, String[] purposes, String[] memos) {
        this.creditors = creditors;
        this.amounts = amounts;
        this.purposes = purposes;
        this.memos = memos;
    }
}

