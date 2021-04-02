package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.tm.Tm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class TmRequestDto {

    private Client client;
    private Long clientSeq;
    private LocalDateTime callDate = LocalDateTime.now();
    private Member caller;
    private String hopeCallTime;
    private String purpose;
    private Integer hopeAmount;
    private String creditStatus;
    private String mainBank;
    private Character arrearsYn;
    private Character loanYn;
    private Integer loanAmount;
    private String houseType;
    private String memo;

    @Builder
    public TmRequestDto(Client client, LocalDateTime callDate, Member caller, String hopeCallTime, String purpose,
                        Integer hopeAmount, String creditStatus, String mainBank, Character arrearsYn,
                        Character loanYn, Integer loanAmount, String houseType, String memo) {
        this.client = client;
        this.callDate = callDate;
        this.caller = caller;
        this.hopeCallTime = hopeCallTime;
        this.purpose = purpose;
        this.hopeAmount = hopeAmount;
        this.creditStatus = creditStatus;
        this.mainBank = mainBank;
        this.arrearsYn = arrearsYn;
        this.loanYn = loanYn;
        this.loanAmount = loanAmount;
        this.houseType = houseType;
        this.memo = memo;
    }

    public void updateClient(Client client) {
        this.client = client;
    }

    public void updateCaller(Member member) {
        this.caller = member;
    }


    public Tm toEntity() {
        return Tm.builder()
                .client(client)
                .caller(caller)
                .callDate(callDate)
                .hopeCallTime(hopeCallTime)
                .hopeAmount(hopeAmount)
                .purpose(purpose)
                .creditStatus(creditStatus)
                .mainBank(mainBank)
                .arrearsYn(arrearsYn)
                .loanYn(loanYn)
                .loanAmount(loanAmount)
                .houseType(houseType)
                .memo(memo)
                .build();
    }
}
