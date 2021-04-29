package com.bizns.bizneyland.config.auth.dto;

import com.bizns.bizneyland.domain.user.Role;
import com.bizns.bizneyland.domain.user.User;
import com.bizns.bizneyland.domain.user.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    @Autowired private UserRepository userRepository;

    private String name;
    private String email;
    private String picture;
    private Long userSeq;
    private Role role;
    private Long memberSeq;
    private Long companySeq;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.userSeq = user.getUserSeq();
        this.role = user.getRole();

        // 세션에 회원번호, 소속 회사번호 등록
        if (user.getMember() != null) {
            this.memberSeq = user.getMember().getId();
        }
    }

    public void setCompanySeq(Long companySeq) {
        this.companySeq = companySeq;
    }
}
