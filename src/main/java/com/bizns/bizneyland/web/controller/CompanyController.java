package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/company")
@Controller
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 회사 목록 화면
     * */
    @GetMapping("/list")
    public void company(Model model) {
        model.addAttribute("companies", companyService.findAllDesc());
    }

    /**
     * 회사 등록 양식
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

        companyService.save(requestDto);

        return "redirect:/company";
    }

}
