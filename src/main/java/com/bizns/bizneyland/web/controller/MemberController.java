package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.domain.user.Role;
import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService service;
    private final CompanyService companyService;

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
        MemberResponseDto member = service.findOne(memberSeq);
        model.addAttribute("member", member);

        return "member/detail";
    }

    /**
     * 가입 코드 확인 화면
     */
    @GetMapping("checkCode")
    public String checkCode() {
        return "member/checkCode";
    }

    /**
     * 가입 코드 확인
     */
    @PostMapping("checkValid")
    @ResponseBody
    public Map<String, Object> isValidCompany(@RequestBody Map<String, String> paramMap) {

        String comSeq = paramMap.get("companySeq");
        String businessNo = paramMap.get("businessNo");

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("isValid", companyService.isValidCompany(Long.parseLong(comSeq), businessNo));

        return returnMap;
    }

    /**
     * 가입 코드 확인
     */
    @PostMapping("checkCode")
    public String checkCode(Model model) {

        return "redirect:/member/register";
    }

    /**
     * 회원 등록 화면
     * */
    @GetMapping("register")
    public void memberForm(MemberCreateRequestDto requestDto, Model model) {
        CompanyResponseDto company = companyService.findById(requestDto.getCompanySeq());
        requestDto.setCompanyName(company.getName());

        model.addAttribute("memberRequestDto", requestDto);
    }

    /**
     * 회원 등록
     * */
    @PostMapping("register")
    public String registerMember(@Valid MemberCreateRequestDto requestDto, @LoginUser SessionUser user) {

        requestDto.setUserSeq(user.getUserSeq());
        service.save(requestDto);

        if (user.getRole() == Role.ADMIN) {
            return "redirect:/member/list";
        }

        return "redirect:/member/mypage";
    }

    /**
     * 회원 수정 화면
     * @param id
     */
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {

        MemberResponseDto member = service.findOne(id);
        model.addAttribute("member", member);

        return "member/update";
    }

    @PostMapping("update")
    public String update(@Valid MemberUpdateRequestDto requestDto) {

        service.update(requestDto.getId(), requestDto);

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

        model.addAttribute("member", service.findOne(member.getId()));
    }
}
