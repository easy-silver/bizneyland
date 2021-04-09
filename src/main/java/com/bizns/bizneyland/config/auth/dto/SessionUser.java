package com.bizns.bizneyland.config.auth.dto;

import com.bizns.bizneyland.domain.user.Role;
import com.bizns.bizneyland.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;
    private Long userSeq;
    private Role role;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.userSeq = user.getUserSeq();
        this.role = user.getRole();
    }
}
