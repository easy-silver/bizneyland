package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import com.bizns.bizneyland.web.dto.CompanyResponseDto;
import com.bizns.bizneyland.web.dto.CompanyUpdateRequestDto;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/company")
@Controller
public class CompanyController {

    private final CompanyService service;
    private final MemberService memberService;

    /**
     * 회사 목록 화면
     * */
    @GetMapping("/list")
    public void list(Model model) {
        model.addAttribute("companies", service.findAllDesc());
    }

    /**
     * 회사 상세 화면
     * */
    @GetMapping("/detail/{companySeq}")
    public String detail(Model model, @PathVariable Long companySeq) {

        CompanyResponseDto company = service.findById(companySeq);
        model.addAttribute("company", company);

        List<MemberResponseDto> members = memberService.findByCompanySeq(companySeq);
        model.addAttribute("members", members);

        return "company/detail";
    }

    /**
     * 회사 등록 화면
     * */
    @GetMapping("register")
    public void register(Model model) {
        model.addAttribute("companyRequestDto", new CompanyRequestDto());
    }

    /**
     * 회사 등록
     * */
    @PostMapping("register")
    public String registerCompany(@Valid CompanyRequestDto requestDto, BindingResult result) {
        if (result.hasErrors())
            return "company/register";

        service.save(requestDto);

        return "redirect:/company/list";
    }

    /**
     * 회사 수정 화면
     */
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {

        CompanyResponseDto company = service.findById(id);
        model.addAttribute("companyUpdateRequestDto", company);

        return "company/update";
    }

    /**
     * 회사 수정
     */
    @PostMapping("update")
    public String update(@Valid CompanyUpdateRequestDto requestDto) {
        service.update(requestDto.getId(), requestDto);

        return "redirect:/company/list";

    }

    /**
     * 회사 삭제
     * */
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/company/list";
    }

}
