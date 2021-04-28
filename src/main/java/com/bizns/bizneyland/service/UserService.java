package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.user.Role;
import com.bizns.bizneyland.domain.user.User;
import com.bizns.bizneyland.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository repository;

    /**
     * 사용자 한 건 조회
     * @param userSeq
     * @return
     */
    public User findById(Long userSeq) {
        return repository.findById(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. USER_SEQ=" + userSeq));
    }

    /**
     * 회원정보 변경
     * @param userSeq
     * @param member
     * @return
     */
    public User updateMemberInfo(Long userSeq, Member member) {
        User user = findById(userSeq);
        return user.setMember(member);
    }

    /**
     * 사용자 권한 변경
     * @param userSeq
     * @param role
     * @return
     */
    public User changeRole(Long userSeq, Role role) {
        User user = findById(userSeq);
        return user.updateRole(role);
    }
}
