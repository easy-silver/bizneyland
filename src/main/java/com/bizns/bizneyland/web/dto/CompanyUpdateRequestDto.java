package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.util.FormatUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter @Getter
public class CompanyUpdateRequestDto {

    private Long id;
    private String businessNo;
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String tel;

    private Long logoFileSeq;

    public void setBusinessNo(String businessNo) {
        this.businessNo = FormatUtil.formatOnlyNumber(businessNo);
    }
    public void setTel(String tel) {
        this.tel = FormatUtil.formatOnlyNumber(tel);
    }
}
