package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.tm.Tm;
import com.bizns.bizneyland.util.FormatUtil;
import lombok.Getter;

@Getter
public class TmResponseDto {

    private Long tmSeq;
    private ClientResponseDto client;
    private MemberResponseDto caller;

    private String callDate;
    private String recipient;
    private String headcount;
    private String purpose;
    private String hopeAmount;
    private Character arrearsYn;
    private String arrearsDetail;
    private String creditStatus;
    private String hopeCallTime;
    private String memo;

    public TmResponseDto(Tm entity) {
        this.tmSeq = entity.getTmSeq();
        this.client = new ClientResponseDto(entity.getClient());
        this.caller = new MemberResponseDto(entity.getCaller());

        this.callDate = FormatUtil.localDateTimeToYmdHms(entity.getCallDate());
        this.recipient = entity.getRecipient();
        this.headcount = entity.getHeadcount();
        this.purpose = entity.getPurpose();
        this.hopeAmount = entity.getHopeAmount();
        this.arrearsYn = entity.getArrearsYn();
        this.arrearsDetail = entity.getArrearsDetail();
        this.creditStatus = entity.getCreditStatus();
        this.hopeCallTime = entity.getHopeCallTime();
        this.memo = entity.getMemo();
    }
}
