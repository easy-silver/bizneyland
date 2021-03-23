package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.company.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CompanyRequestDto {

    private String name;
    private String address;
    private String tel;
    private Long logoFileSeq;
    private String businessNo;

    @Builder
    public CompanyRequestDto(String name, String address, String tel, Long logoFileSeq, String businessNo) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.logoFileSeq = logoFileSeq;
        this.businessNo = businessNo;
    }

    public Company toEntity() {
        return Company.builder()
                .name(name)
                .address(address)
                .tel(tel)
                .logoFileSeq(logoFileSeq)
                .businessNo(businessNo)
                .build();
    }
}