package com.bizns.bizneyland.domain.company;


import com.bizns.bizneyland.domain.BaseTimeEntity;
import com.bizns.bizneyland.domain.owner.Owner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class Company extends BaseTimeEntity {

    /* 회사 일련번호(PK) */
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "company_seq")
    private Long id;

    /* 대표자 일련번호(FK) */
    @OneToOne(mappedBy = "company")
    private Owner owner;

    // 사업자 번호
    @Column(unique = true, nullable = false, length = 12)
    private String businessNo;

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

    public void update(String address, String tel, Long logoFileSeq) {
        this.address = address;
        this.tel = tel;
        this.logoFileSeq = logoFileSeq;
    }
}
