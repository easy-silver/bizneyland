package com.bizns.springboot.web.dto;

import com.bizns.springboot.domain.company.Company;
import lombok.Getter;

@Getter
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String address;
    private String tel;
    private Long logoFileSeq;
    private String businessNo;

    public CompanyResponseDto(Company entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.tel = entity.getTel();
        this.logoFileSeq = entity.getLogoFileSeq();
        this.businessNo = entity.getBusinessNo();
    }
}
