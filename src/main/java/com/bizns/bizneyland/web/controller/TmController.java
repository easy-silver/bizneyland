package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.service.OwnerService;
import com.bizns.bizneyland.service.TmService;
import com.bizns.bizneyland.web.dto.ClientRequestDto;
import com.bizns.bizneyland.web.dto.OwnerRequestDto;
import com.bizns.bizneyland.web.dto.TmRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/tm")
@Controller
public class TmController {

    private final ClientService clientService;
    private final OwnerService ownerService;
    private final TmService tmService;
    private final MemberRepository memberRepository;

    @GetMapping("list")
    public void list(Model model) {
        model.addAttribute("tmList", tmService.findAllDesc());
    }

    @GetMapping("form")
    public String clientForm() {
        return "tm/registerClient";
    }

    @PostMapping("registerClient")
    public String registerClient(ClientRequestDto clientDto, OwnerRequestDto ownerDto, RedirectAttributes redirectAttributes) {

        // INSERT CLIENT
        Client client = clientService.save(clientDto);

        // INSERT OWNER
        ownerDto.updateClient(client);
        Long ownerSeq = ownerService.save(ownerDto);

        System.out.println("clientSeq = " + client.getClientSeq());
        System.out.println("ownerSeq = " + ownerSeq);

        redirectAttributes.addAttribute("clientSeq", client.getClientSeq());

        return "redirect:/tm/form/tmInfo";
    }

    @GetMapping("form/tmInfo")
    public String tmForm(@ModelAttribute("clientSeq") Long clientSeq, Model model) {
        model.addAttribute("clientSeq", clientSeq);

        return "tm/registerTmInfo";
    }

    @PostMapping("registerTmInfo")
    public String registerTmInfo(TmRequestDto requestDto, @LoginUser SessionUser user) {

        // 세션에 있는 USER_SEQ로 Member 찾기
        Member caller = memberRepository.findByUserSeq(user.getUserSeq());
        requestDto.updateCaller(caller);

        Long clientSeq = requestDto.getClientSeq();
        Client client = clientService.findById(clientSeq);
        requestDto.updateClient(client);

        tmService.save(requestDto);

        return "redirect:/tm/list";
    }

    @GetMapping("form/sales")
    public String salesForm() {
        return "tm/registerSales";
    }

    @PostMapping("registerSales")
    public String registerSales() {
        return "redirect:/tm/list";
    }
}
