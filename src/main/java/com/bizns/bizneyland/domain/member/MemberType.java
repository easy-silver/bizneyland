package com.bizns.bizneyland.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 구분
 */
@Getter
@RequiredArgsConstructor
public enum MemberType {
    OWNER("대표자"),
    TM("상담사"),
    SALES("영업사원");

    private final String title;
}
