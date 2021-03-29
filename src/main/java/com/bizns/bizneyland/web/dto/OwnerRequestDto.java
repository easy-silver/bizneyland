package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.owner.Owner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class OwnerRequestDto {

    private Client client;
    private String ownerName;
    private String ownerMobile;

    @Builder
    public OwnerRequestDto(String ownerName, String ownerMobile) {
        this.ownerName = ownerName;
        this.ownerMobile = ownerMobile;
    }

    public void updateClient(Client client) {
        this.client = client;
    }

    public Owner toEntity() {
        return Owner.builder()
                .client(client)
                .name(ownerName)
                .mobile(ownerMobile)
                .build();
    }
}
