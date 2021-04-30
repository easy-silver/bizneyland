package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.domain.user.Role;
import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.LoanService;
import com.bizns.bizneyland.service.SalesService;
import com.bizns.bizneyland.service.TmService;
import com.bizns.bizneyland.web.dto.ClientCreateRequestDto;
import com.bizns.bizneyland.web.dto.TmCreateRequestDto;
import com.bizns.bizneyland.web.dto.TmResponseDto;
import com.bizns.bizneyland.web.dto.TmUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/tm")
@Controller
public class TmController {

    private final TmService service;
    private final ClientService clientService;
    private final SalesService salesService;
    private final LoanService loanService;

    /**
     * TM 상담 목록
     */
    @GetMapping("list")
    public void list(@LoginUser SessionUser user, Model model) {
        model.addAttribute("tmList",
                user.getRole() == Role.ADMIN ? service.findAllDesc()
                        : user.getRole() == Role.OWNER ? service.findByCompany(user.getCompanySeq())
                        : service.findByCaller(user.getMemberSeq()));
    }

    /**
     * TM 상세
     */
    @GetMapping("detail/{tmNo}")
    public String detail(Model model, @PathVariable Long tmNo) {
        TmResponseDto tm = service.findById(tmNo);
        model.addAttribute("tm", tm);
        model.addAttribute("client", tm.getClient());
        model.addAttribute("salesList", salesService.findAllByClient(tm.getClient().getClientSeq()));
        model.addAttribute("loanList", loanService.findAllByTm(tm.getTmSeq()));
        return "tm/detail";
    }

    /**
     * TM 등록 화면
     */
    @GetMapping("register")
    public void register(@ModelAttribute(name="contact") String contact) { }

    /**
     * TM 등록
     */
    @PostMapping("register")
    public String register(ClientCreateRequestDto clientDto, TmCreateRequestDto tmDto) {

        // 고객(업체) 정보 및 매출 정보 등록
        Long clientSeq = clientService.save(clientDto);
        tmDto.setClientSeq(clientSeq);
        service.save(tmDto);

        return "redirect:/tm/list";
    }

    /**
     * TM 수정 화면
     */
    @GetMapping("update/{seq}")
    public String update(@PathVariable Long seq, Model model) {
        TmResponseDto tm = service.findById(seq);
        model.addAttribute("tm", tm);
        model.addAttribute("loanList", loanService.findAllByTm(seq));

        return "tm/update";
    }

    /**
     * TM 수정
     */
    @PostMapping("update")
    public String update(TmUpdateRequestDto requestDto) {
        service.updateTmInfo(requestDto);

        return "redirect:/tm/detail/" + requestDto.getTmSeq();
    }

    /**
     * TM 삭제
     */
    @GetMapping("delete/{seq}")
    public String delete(@PathVariable Long seq) {
        service.delete(seq);

        return "redirect:/tm/list";
    }

    /**
     * [TEST] 대출 정보 등록 화면
     */
    @GetMapping("loan/register")
    public void registerLoanInfo() {

    }

    /**
     * [TEST] 대출 정보 등록
     */
    @PostMapping("loan/register")
    public void registerLoanInfo(TmCreateRequestDto requestDto) {
        service.save(requestDto);
    }
}
