package com.bizns.bizneyland.domain.client;

import com.bizns.bizneyland.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


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

    // 업체 구분 개인(P) 법인(C)
    private Character type;

    // 설립일
    private LocalDate establishDate;

    // 업종
    private String sector;

    // 주소
    private String address;

    // 전화번호
    private String tel;

    // 주요 품목
    private String keyItem;

    @Builder
    public Client(String companyName, Character type, LocalDate establishDate, String sector,
                  String address, String tel, String keyItem) {
        this.companyName = companyName;
        this.type = type;
        this.establishDate = establishDate;
        this.sector = sector;
        this.address = address;
        this.tel = tel;
        this.keyItem = keyItem;
    }
}
