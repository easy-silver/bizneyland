package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import com.bizns.bizneyland.web.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registerCompany(CompanyRequestDto requestDto, RedirectAttributes redirectAttributes) {
        Long companyId = companyService.save(requestDto);
        redirectAttributes.addAttribute("companyId", companyId);

        return "redirect:/join/member";
    }

    /**
     * 회원 등록 화면
     * */
    @GetMapping("member")
    public String memberForm(@ModelAttribute("companyId") String companyId, Model model) {
        model.addAttribute("companyId", companyId);

        return "join/formMember";
    }

    /**
     * 회원 등록
     * */
    @PostMapping("member")
    public String registerMember(@RequestParam("isCeo") String isCeo, MemberSaveRequestDto requestDto) {
        Long memberId = memberService.save(requestDto);
        Long companyId = requestDto.getCompanyId();

        if (Boolean.valueOf(isCeo))
            companyService.updateCeoId(companyId, memberId);

        return "redirect:/member";
    }

}
