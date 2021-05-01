package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.util.FormatUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateRequestDto {

    private Long id;
    private String name;
    private String nickname;
    private String birth;
    private String gender;
    private String mobile;
    private String grade;
    private String workingArea;
    private String email;
    private String fax;
    private Long profileFileSeq;

    public void setMobile(String mobile) {
        this.mobile = FormatUtil.formatOnlyNumber(mobile);
    }
    public void setFax(String fax) {
        this.fax = FormatUtil.formatOnlyNumber(fax);
    }
}
