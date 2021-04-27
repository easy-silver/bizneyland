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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_seq", nullable = false)
    private Client client;

    // 발생 연도
    @Column(length = 4, nullable = false)
    private String salesYear;

    // 금액
    @Column(nullable = false)
    private String amount;

    @Builder
    public Sales(Client client, String salesYear, String amount) {
        this.client = client;
        this.salesYear = salesYear;
        this.amount = amount;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
