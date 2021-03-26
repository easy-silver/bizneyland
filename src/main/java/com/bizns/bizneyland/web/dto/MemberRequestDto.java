package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberRequestDto {

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
    private Long companyId;

    @Builder
    public MemberRequestDto(String name, String nickname, String birth, String gender,
                            String mobile, String grade, String workingArea, String email,
                            String fax, Long profileFileSeq, Long companyId) {
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.mobile = mobile;
        this.grade = grade;
        this.workingArea = workingArea;
        this.email = email;
        this.fax = fax;
        this.profileFileSeq = profileFileSeq;
        this.companyId = companyId;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .mobile(mobile)
                .grade(grade)
                .workingArea(workingArea)
                .email(email)
                .fax(fax)
                .profileFileSeq(profileFileSeq)
                .company(new Company(companyId))
                .build();
    }
}
