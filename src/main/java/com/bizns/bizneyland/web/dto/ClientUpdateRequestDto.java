package com.bizns.bizneyland.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientUpdateRequestDto {

    private Long clientSeq;
    private String companyName;
    private String contact;
    private String owner;
    private String address;
    private Character type;
    private String establishDate;
    private String sector;
    private String keyItem;

}
