package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.TmService;
import com.bizns.bizneyland.web.dto.ClientCreateRequestDto;
import com.bizns.bizneyland.web.dto.TmRequestDto;
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

    private final ClientService clientService;
    private final TmService tmService;

    /**
     * TM 상담 목록
     */
    @GetMapping("list")
    public void list(Model model) {
        model.addAttribute("tmList", tmService.findAllDesc());
    }

    /**
     * TM 상세
     */
    @GetMapping("detail/{tmNo}")
    public String tmDetail(Model model, @PathVariable Long tmNo) {
        TmResponseDto tm = tmService.findById(tmNo);
        Long clientNo = tm.getClientSeq();
        clientService.findById(clientNo);
        model.addAttribute("tm", tm);
        model.addAttribute("client", clientService.findById(clientNo));
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
    public String register(ClientCreateRequestDto clientDto, TmRequestDto tmDto) {

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
    public String registerTmInfo(TmRequestDto requestDto, @LoginUser SessionUser user) {

        requestDto.setUserSeq(user.getUserSeq());
        tmService.save(requestDto);

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

}
