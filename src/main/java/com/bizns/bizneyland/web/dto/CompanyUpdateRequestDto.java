package com.bizns.bizneyland.web.dto;

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
}
