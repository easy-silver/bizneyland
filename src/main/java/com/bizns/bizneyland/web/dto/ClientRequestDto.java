package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.client.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class ClientRequestDto {

    @NotEmpty(message = "회사명은 필수입니다.")
    private String companyName;
    // 전화번호
    private String tel;
    // 업체 구분 개인(P) 법인(C)
    private Character type;
    // 설립일
    private LocalDate establishDate;
    // 업종
    private String sector;
    // 주요 품목
    private String keyItem;
    // 주소
    private String address;

    // 대표자명
    //private String ownerName;
    // 대표자 연락처
    //private String ownerMobile;

    @Builder
    public ClientRequestDto(String companyName, Character type, LocalDate establishDate,
                            String sector, String address, String tel, String keyItem) {
        this.companyName = companyName;
        this.type = type;
        this.establishDate = establishDate;
        this.sector = sector;
        this.address = address;
        this.tel = tel;
        this.keyItem = keyItem;
    }

    public Client toEntity() {
        return Client.builder()
                .companyName(companyName)
                .type(type)
                .establishDate(establishDate)
                .sector(sector)
                .address(address)
                .tel(tel)
                .keyItem(keyItem)
                .build();
    }
}
