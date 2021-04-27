package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.SalesService;
import com.bizns.bizneyland.service.TmService;
import com.bizns.bizneyland.web.dto.ClientCreateRequestDto;
import com.bizns.bizneyland.web.dto.TmCreateRequestDto;
import com.bizns.bizneyland.web.dto.TmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/tm")
@Controller
public class TmController {

    private final TmService service;
    private final ClientService clientService;
    private final SalesService salesService;

    /**
     * TM 상담 목록
     */
    @GetMapping("list")
    public void list(Model model) {
        model.addAttribute("tmList", service.findAllDesc());
    }

    /**
     * TM 상세
     */
    @GetMapping("detail/{tmNo}")
    public String tmDetail(Model model, @PathVariable Long tmNo) {
        TmResponseDto tm = service.findById(tmNo);
        model.addAttribute("tm", tm);
        model.addAttribute("client", tm.getClient());
        model.addAttribute("salesList", salesService.findAllByClient(tm.getClient().getClientSeq()));
        return "tm/detail";
    }

    /**
     * TM 등록 양식
     */
    @GetMapping("register")
    public void register() {
    }

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
     * TM 등록 - 업체 등록
     */
    @PostMapping("register/client")
    public String registerClient(ClientCreateRequestDto clientDto, RedirectAttributes redirectAttributes) {

        Client client;

        // 고객 회사명으로 조회 후 있으면 UPDATE, 없으면 INSERT
        /*if (clientService.isExist(clientDto.getCompanyName())) {
            client = clientService.updateByName(clientDto.getCompanyName(), clientDto);
        } else {
            client = clientService.save(clientDto);
        }*/

        //redirectAttributes.addAttribute("clientSeq", client.getClientSeq());

        return "redirect:/tm/register/tmInfo";
    }

    /**
     * TM 등록(상담 정보) 양식
     */
    @GetMapping("register/tmInfo")
    public String tmForm(@ModelAttribute("clientSeq") Long clientSeq, Model model) {
        model.addAttribute("clientSeq", clientSeq);

        return "tm/register/tmInfo";
    }

    /**
     * TM 등록(상담 정보) 등록
     */
    @PostMapping("register/tmInfo")
    public String registerTmInfo(TmCreateRequestDto requestDto, @LoginUser SessionUser user) {

        requestDto.setUserSeq(user.getUserSeq());
        service.save(requestDto);

        return "redirect:/tm/list";
    }

    /**
     * TM 등록(매출 정보) 양식
     */
    @GetMapping("register/sales")
    public String registerSales(Model model) {
        return "tm/register/sales";
    }

    /**
     * TM 등록(매출 정보) 등록
     */
    @PostMapping("register/sales")
    public String registerSales() {
        return "redirect:/tm/list";
    }

    /**
     * 대출 정보 등록 화면
     */
    @GetMapping("loan/register")
    public void registerLoanInfo() {

    }

    /**
     * 대출 정보 등록
     */
    @PostMapping("loan/register")
    public void registerLoanInfo(TmCreateRequestDto requestDto) {
        service.save(requestDto);
    }
}
