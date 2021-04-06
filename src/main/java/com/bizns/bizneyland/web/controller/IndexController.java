package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final CompanyService companyService;
    private final MemberService memberService;

    @GetMapping("/")
    public String index(@LoginUser SessionUser user, Model model) {
        if (user != null) {
            if(user.getUserSeq() == 0L)
                return "redirect:/admin";
            MemberResponseDto member = memberService.findByUserSeq(user.getUserSeq());
            model.addAttribute("user", user);
            model.addAttribute("member", member);
        }
        return "main";
    }

    @GetMapping("/admin")
    public String admin(@LoginUser SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "admin";
    }

    @GetMapping("/member")
    public String member(Model model) {
        model.addAttribute("members", memberService.findAllDesc());
        return "member/member";
    }

    @GetMapping("/company")
    public String company(Model model) {
        model.addAttribute("companies", companyService.findAllDesc());
        return "company/company";
    }
}
