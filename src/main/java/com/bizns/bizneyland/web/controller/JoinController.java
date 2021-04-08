package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/join")
@Controller
public class JoinController {

    private final CompanyService companyService;
    private final MemberService memberService;

    /**
     * 회원 등록 화면
     * */
    @GetMapping("member")
    public String memberForm() {

        return "join/formMember";
    }

    /**
     * 회원 등록
     * */
    @PostMapping("member")
    public String registerMember(MemberRequestDto requestDto, @LoginUser SessionUser user) {

        requestDto.setUserSeq(user.getUserSeq());
        memberService.save(requestDto);

        return "redirect:/member";
    }

}
