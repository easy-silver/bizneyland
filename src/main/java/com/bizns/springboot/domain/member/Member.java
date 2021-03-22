package com.bizns.springboot.domain.member;

import com.bizns.springboot.domain.BaseTimeEntity;
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

    @Builder
    public Member(String name, String nickname, String birth, String gender,
                  String mobile, String grade, String workingArea, String email,
                  String fax, Long profileFileSeq) {
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
    }

    public void update(String name, String nickname, String birth, String gender,
                       String mobile, String grade, String workingArea, String email,
                       String fax, Long profileFileSeq) {
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
    }
}
