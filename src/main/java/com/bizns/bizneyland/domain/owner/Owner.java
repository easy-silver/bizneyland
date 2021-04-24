package com.bizns.bizneyland.domain.owner;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
public class Owner {

    /* 대표자 번호(PK) */
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "owner_seq")
    private Long id;

    /* 회사 번호 */
    @OneToOne
    @JoinColumn(name = "company_seq", nullable = false, unique = true)
    private Company company;

    /* 회원 번호 */
    @OneToOne
    @JoinColumn(name = "member_seq", nullable = false, unique = true)
    private Member member;

    /* 대표자 이름 */
    @Column(nullable = false)
    private String name;

    @Builder
    public Owner(Company company, Member member, String name) {
        this.company = company;
        this.member = member;
        this.name = name;
    }
}
