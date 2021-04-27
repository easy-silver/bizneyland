package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.SalesService;
import com.bizns.bizneyland.service.TmService;
import com.bizns.bizneyland.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/client")
@Controller
public class ClientController {

    private final ClientService service;
    private final SalesService salesService;
    private final TmService tmService;

    /**
     * 고객 목록
     * */
    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("clients", service.findAll());
        return "client/list";
    }

    /**
     * 고객 상세
     * */
    @GetMapping("detail/{clientSeq}")
    public String detail(@PathVariable Long clientSeq, Model model) {
        ClientResponseDto client = service.findById(clientSeq);
        model.addAttribute("client", client);

        List<TmResponseDto> tms = tmService.findByClient(client.getClientSeq());
        model.addAttribute("tms", tms);

        model.addAttribute("salesList", salesService.findAllByClient(clientSeq));

        return "client/detail";
    }

    /**
     * 고객 등록 양식
     * */
    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("clientRequestDto", new ClientCreateRequestDto());
        return "client/register";
    }

    /**
     * 고객 등록
     * */
    @PostMapping("register")
    public String register(ClientCreateRequestDto clientDto) {

        service.save(clientDto);

        return "redirect:/client/list";
    }

    /**
     * 고객 수정 화면
     */
    @GetMapping("update/{seq}")
    public String update(@PathVariable Long seq, Model model) {
        ClientResponseDto client = service.findById(seq);
        model.addAttribute("client", client);

        return "client/update";
    }

    /**
     * 고객 수정
     */
    @PostMapping("update")
    public String update(@Valid ClientUpdateRequestDto requestDto) {
        service.update(requestDto.getClientSeq(), requestDto);

        return "redirect:/client/list";
    }

    /**
     * 고객 삭제
     * */
    @GetMapping("delete/{clientSeq}")
    public String delete(@PathVariable Long clientSeq) {

        service.deleteById(clientSeq);

        return "redirect:/client/list";
    }
}
