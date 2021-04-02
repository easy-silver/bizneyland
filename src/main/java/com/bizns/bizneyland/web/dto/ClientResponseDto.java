package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.client.Client;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ClientResponseDto {

    private Long clientSeq;
    private String companyName;
    private Character type;
    private LocalDate establishDate;
    private String sector;
    private String address;
    private String tel;
    private String keyItem;

    public ClientResponseDto(Client entity) {
        this.clientSeq = entity.getClientSeq();
        this.companyName = entity.getCompanyName();
        this.type = entity.getType();
        this.establishDate = entity.getEstablishDate();
        this.sector = entity.getSector();
        this.address = entity.getAddress();
        this.tel = entity.getTel();
        this.keyItem = entity.getKeyItem();
    }
}
