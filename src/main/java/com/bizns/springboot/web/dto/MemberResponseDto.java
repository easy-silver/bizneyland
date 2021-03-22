package com.bizns.springboot.web.dto;

import com.bizns.springboot.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

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

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.birth = entity.getBirth();
        this.gender = entity.getGender();
        this.mobile = entity.getMobile();
        this.grade = entity.getGrade();
        this.workingArea = entity.getWorkingArea();
        this.email = entity.getEmail();
        this.fax = entity.getFax();
        this.profileFileSeq = entity.getProfileFileSeq();
    }
}
