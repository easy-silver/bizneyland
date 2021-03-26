package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import com.bizns.bizneyland.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String companyForm(Model model) {
        model.addAttribute("companyRequestDto", new CompanyRequestDto());
        return "join/formCompany";
    }

    /**
     * 회사 등록
     * */
    @PostMapping("company")
    public String registerCompany(@Valid CompanyRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "join/formCompany";
        }

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
    public String registerMember(@RequestParam("isCeo") String isCeo, MemberRequestDto requestDto) {
        Long memberId = memberService.save(requestDto);
        Long companyId = requestDto.getCompanyId();

        if (Boolean.valueOf(isCeo))
            companyService.updateCeoId(companyId, memberId);

        return "redirect:/member";
    }

}
