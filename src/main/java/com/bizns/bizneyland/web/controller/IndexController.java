package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;
    private final CompanyService companyService;
    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "main";
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
