package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.owner.Owner;
import lombok.Getter;

@Getter
public class OwnerResponseDto {

    private Long ownerSeq;
    private Client client;
    private String name;
    private String mobile;

    public OwnerResponseDto(Owner entity) {
        this.ownerSeq = entity.getOwnerSeq();
        this.client = entity.getClient();
        this.name = entity.getName();
        this.mobile = entity.getMobile();
    }
}
