package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.util.FormatUtil;
import lombok.Getter;

@Getter
public class ClientResponseDto {

    private Long clientSeq;
    private String companyName;
    private String contact;
    private String owner;
    private Character type;
    private String establishDate;
    private String sector;
    private String address;
    private String keyItem;

    public ClientResponseDto(Client entity) {
        this.clientSeq = entity.getClientSeq();
        this.companyName = entity.getCompanyName();
        this.contact = entity.getContact();
        this.owner = entity.getOwner();
        this.type = entity.getType();
        this.establishDate = entity.getEstablishDate();
        this.sector = entity.getSector();
        this.address = entity.getAddress();
        this.keyItem = entity.getKeyItem();
    }

    public String getContact() {
        return FormatUtil.addHyphenPhoneNumber(contact);
    }
}
