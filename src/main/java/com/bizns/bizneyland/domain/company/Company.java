package com.bizns.bizneyland.domain.company;


import com.bizns.bizneyland.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Company extends BaseTimeEntity {

    // 회사 일련번호(PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 회사명
    @Column(nullable = false)
    private String name;
    // 주소
    @Column
    private String address;
    // 전화번호
    @Column
    private String tel;
    // 회사로고
    @Column
    private Long logoFileSeq;
    // 사업자 번호
    @Column
    private String businessNo;
    @Column
    private Long ceoMemberId;

    public Company(Long id) {
        this.id = id;
    }

    @Builder
    public Company(String name, String address, String tel, Long logoFileSeq, String businessNo) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.logoFileSeq = logoFileSeq;
        this.businessNo = businessNo;
    }

    public void updateCeoMemberId(Long memberId) {
        this.ceoMemberId = memberId;
    }

    public void update(String address, String tel, Long logoFileSeq) {
        this.address = address;
        this.tel = tel;
        this.logoFileSeq = logoFileSeq;
    }
}
