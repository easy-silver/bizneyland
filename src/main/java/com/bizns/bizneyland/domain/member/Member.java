package com.bizns.bizneyland.domain.member;

import com.bizns.bizneyland.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 이름
    @Column
    private String name;

    // 닉네임
    @Column
    private String nickname;

    // 생년월일
    @Column
    private String birth;

    // 성별
    @Column
    private String gender;

    // 휴대폰 번호
    @Column
    private String mobile;

    // 직급 및 직책
    @Column
    private String grade;

    // 활동 지역
    @Column
    private String workingArea;

    // 이메일
    @Column
    private String email;

    // 팩스 번호
    @Column
    private String fax;

    // 프로필 사진
    @Column
    private Long profileFileSeq;

    // 소속 회사 일련번호(FK)
    @Column(nullable = false)
    private Long companyId;

    @Builder
    public Member(String name, String nickname, String birth, String gender,
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

    public void update(String nickname, String birth, String gender,
                       String mobile, String grade, String workingArea, String email,
                       String fax, Long profileFileSeq) {
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.mobile = mobile;
        this.grade = grade;
        this.workingArea = workingArea;
        this.email = email;
        this.fax = fax;
        this.profileFileSeq = profileFileSeq;
    }
}
