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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/company")
@Controller
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("companyRequestDto", new CompanyRequestDto());
        return "join/formCompany";
    }

    /**
     * 회사 등록
     * */
    @PostMapping("company")
    public String registerCompany(@Valid CompanyRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
            return "join/formCompany";

        companyService.save(requestDto);

        return "redirect:/company";
    }

    /**
     * 회사 목록 화면
     * */
    @GetMapping("/company")
    public String company(Model model) {
        model.addAttribute("companies", companyService.findAllDesc());
        return "company/company";
    }


}
