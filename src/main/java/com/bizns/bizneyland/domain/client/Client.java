package com.bizns.bizneyland.domain.client;

import com.bizns.bizneyland.domain.BaseTimeEntity;
import com.bizns.bizneyland.web.dto.ClientUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Client extends BaseTimeEntity {

    // 고객 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientSeq;

    // 회사명
    @Column(nullable = false)
    private String companyName;

    // 전화번호
    @Column(nullable = false)
    private String contact;

    // 대표자
    private String owner;

    // 업체 구분 개인(P) 법인(C)
    private Character type;

    // 설립일
    private String establishDate;

    // 업종
    private String sector;

    // 주소
    private String address;

    // 주요 품목
    private String keyItem;

    @Builder
    public Client(String companyName, String contact, String owner, Character type,
                  String establishDate, String sector, String address, String keyItem) {
        this.companyName = companyName;
        this.contact = contact;
        this.owner = owner;
        this.type = type;
        this.establishDate = establishDate;
        this.sector = sector;
        this.address = address;
        this.keyItem = keyItem;
    }

    public void update(ClientUpdateRequestDto dto) {
        this.companyName = dto.getCompanyName();
        this.owner = dto.getOwner();
        this.type = dto.getType();
        this.establishDate = dto.getEstablishDate();
        this.sector = dto.getSector();
        this.address = dto.getAddress();
        this.contact = dto.getContact();
        this.keyItem = dto.getKeyItem();
    }

    public Client(Long clientSeq) {
        this.clientSeq = clientSeq;
    }
}
