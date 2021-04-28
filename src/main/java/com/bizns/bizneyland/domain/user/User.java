package com.bizns.bizneyland.domain.user;

import com.bizns.bizneyland.domain.BaseTimeEntity;
import com.bizns.bizneyland.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    /* 사용자 번호(PK) */
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_seq")
    private Long userSeq;

    /* 로그인한 구글 이메일 */
    @Column(nullable = false)
    private String email;

    /* 구글 계정 이름 */
    @Column(nullable = false)
    private String name;

    /* 구글 계정 프로필 사진 주소 */
    @Column
    private String picture;

    /* 사용자 권한(역할) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /* 회원 번호(FK: MEMBER.MEMBER_SEQ) */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    /** 등급(역할) 변경 */
    public User updateRole(Role role) {
        this.role = role;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    /** 회원정보 변경 */
    public User setMember(Member member) {
        this.member = member;
        return this;
    }

}
