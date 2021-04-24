package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.util.FormatUtil;
import lombok.Getter;

@Getter
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String address;
    private String tel;
    private Long logoFileSeq;
    private String businessNo;
    private String ownerName;
    private int memberCount;

    public CompanyResponseDto(Company entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.tel = FormatUtil.addHyphenPhoneNumber(entity.getTel());
        this.logoFileSeq = entity.getLogoFileSeq();
        this.businessNo = entity.getBusinessNo();
        this.ownerName = entity.getOwner() == null ? null : entity.getOwner().getName();
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}
