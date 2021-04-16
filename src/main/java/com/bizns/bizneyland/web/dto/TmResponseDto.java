package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.tm.Tm;
import com.bizns.bizneyland.util.FormatUtil;
import lombok.Getter;

@Getter
public class TmResponseDto {

    private Long tmSeq;
    private Client client;
    private Long clientSeq;
    private String callDate;
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

    public TmResponseDto(Tm entity) {
        this.tmSeq = entity.getTmSeq();
        this.client = entity.getClient();
        this.clientSeq = client.getClientSeq();
        this.callDate = FormatUtil.localDateTimeToYmdHms(entity.getCallDate());
        this.caller = entity.getCaller();
        this.hopeCallTime = entity.getHopeCallTime();
        this.purpose = entity.getPurpose();
        this.hopeAmount = entity.getHopeAmount();
        this.creditStatus = entity.getCreditStatus();
        this.mainBank = entity.getMainBank();
        this.arrearsYn = entity.getArrearsYn();
        this.loanYn = entity.getLoanYn();
        this.loanAmount = entity.getLoanAmount();
        this.houseType = entity.getHouseType();
        this.memo = entity.getMemo();
    }
}
