package com.bizns.bizneyland.domain.owner;

import com.bizns.bizneyland.domain.client.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Owner {

    // 대표자 번호(PK)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerSeq;

    // 업체 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_seq", nullable = false)
    private Client client;

    // 대표자명
    @Column(nullable = false)
    private String name;

    // 휴대폰 번호
    private String mobile;

    @Builder
    public Owner(Client client, String name, String mobile) {
        this.client = client;
        this.name = name;
        this.mobile = mobile;
    }
}
