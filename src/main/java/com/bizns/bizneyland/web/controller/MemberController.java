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
import org.springframework.web.bind.annotation.PathVariable;
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
     * 회원 상세
     * */
    @GetMapping("detail/{memberSeq}")
    public String detail(@PathVariable Long memberSeq, Model model) {
        MemberResponseDto member = service.findById(memberSeq);
        model.addAttribute("member", member);

        return "member/detail";
    }

    /**
     * 회원 등록 화면
     * */
    @GetMapping("register")
    public void memberForm() {}

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
     * 회원 삭제(PK)
     * @param id
     */
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);

        return "redirect:/member/list";
    }

    /**
     * 마이페이지
     * */
    @GetMapping("/mypage")
    public void mypage(@LoginUser SessionUser user, Model model) {
        MemberResponseDto member = service.findByUserSeq(user.getUserSeq());

        model.addAttribute("member", service.findById(member.getId()));
    }
}
