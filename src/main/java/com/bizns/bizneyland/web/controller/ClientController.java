package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.TmService;
import com.bizns.bizneyland.web.dto.ClientRequestDto;
import com.bizns.bizneyland.web.dto.ClientResponseDto;
import com.bizns.bizneyland.web.dto.TmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/client")
@Controller
public class ClientController {

    private final ClientService service;
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

        return "client/detail";
    }

    /**
     * 고객 등록 양식
     * */
    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("clientRequestDto", new ClientRequestDto());
        return "client/register";
    }

    /**
     * 고객 등록
     * */
    @PostMapping("register")
    public String registerClient(ClientRequestDto clientDto) {

        service.save(clientDto);

        return "redirect:/client/list";
    }
}
