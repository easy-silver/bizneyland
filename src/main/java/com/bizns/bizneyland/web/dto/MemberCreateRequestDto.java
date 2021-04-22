package com.bizns.bizneyland.web.dto;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberCreateRequestDto {

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

    // 사용자 정보
    private Long userSeq;

    // 회사 정보
    private Long companySeq;
    private String companyName;

    @Builder
    public MemberCreateRequestDto(String name, String nickname, String birth, String gender,
                            String mobile, String grade, String workingArea, String email,
                            String fax, Long profileFileSeq, Long companySeq, Long userSeq) {
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
        this.companySeq = companySeq;
        this.userSeq = userSeq;
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
                .company(new Company(companySeq))
                .userSeq(userSeq)
                .build();
    }

}
