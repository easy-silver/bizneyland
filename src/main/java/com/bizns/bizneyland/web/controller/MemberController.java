package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.MemberRequestDto;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService service;

    /**
     * 회원 목록
     * */
    @GetMapping("list")
    public void member(Model model) {
        model.addAttribute("members", service.findAllDesc());
    }

    /**
     * 회원 등록 화면
     * */
    @GetMapping("register")
    public void memberForm() {
    }

    /**
     * 회원 등록
     * */
    @PostMapping("register")
    public String registerMember(MemberRequestDto requestDto, @LoginUser SessionUser user) {

        requestDto.setUserSeq(user.getUserSeq());
        service.save(requestDto);

        return "redirect:/member/list";
    }

    /**
     * 마이페이지
     * */
    @GetMapping("/mypage")
    public void mypage(@LoginUser SessionUser user, Model model) {
        MemberResponseDto member = service.findByUserSeq(user.getUserSeq());

        // TODO : 회원 정보가 없을 때 처리 필요
        if (member == null) {

        }

        model.addAttribute("member", member);
    }
}
