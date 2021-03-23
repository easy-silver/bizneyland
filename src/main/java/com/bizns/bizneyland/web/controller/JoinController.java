package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import com.bizns.bizneyland.web.dto.MemberSaveRequestDto;
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
     * 회사 등록 화면
     * */
    @GetMapping("company")
    public String companyForm() {
        return "join/formCompany";
    }

    /**
     * 회사 등록
     * */
    @PostMapping("company")
    public String registerCompany(CompanyRequestDto requestDto) {

        companyService.save(requestDto);

        return "redirect:/company";
    }

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
    public String registerMember(MemberSaveRequestDto requestDto) {
        memberService.save(requestDto);
        return "redirect:/member";
    }

}
