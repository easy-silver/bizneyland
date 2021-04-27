package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.company.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class CompanyCreateRequestDto {

    @NotEmpty(message = "사업자 번호는 필수입니다.")
    @Size(min = 12, max = 12, message = "10자리를 정확히 입력해주세요.")
    private String businessNo;

    @NotEmpty(message = "회사 이름은 필수입니다.")
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String tel;

    private Long logoFileSeq;

    @Builder
    public CompanyCreateRequestDto(String name, String address, String tel, Long logoFileSeq, String businessNo) {
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
