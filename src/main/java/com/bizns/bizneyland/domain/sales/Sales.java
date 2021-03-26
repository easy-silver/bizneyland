package com.bizns.bizneyland.domain.sales;

import com.bizns.bizneyland.domain.client.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Sales {

    // PK
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesSeq;

    // 업체 정보(FK)
    @ManyToOne
    @JoinColumn(name = "client_seq", nullable = false)
    private Client client;

    // 발생 연도
    @Column(length = 4, nullable = false)
    private String year;

    // 금액
    @Column(nullable = false)
    private Integer amount;

    @Builder
    public Sales(Client client, String year, Integer amount) {
        this.client = client;
        this.year = year;
        this.amount = amount;
    }
}