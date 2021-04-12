package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.OwnerService;
import com.bizns.bizneyland.web.dto.ClientRequestDto;
import com.bizns.bizneyland.web.dto.OwnerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/client")
@Controller
public class ClientController {

    private final ClientService service;
    private final OwnerService ownerService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("clients", service.findAll());
        return "client/list";
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
    public String registerClient(ClientRequestDto clientDto, OwnerRequestDto ownerDto) {

        // INSERT CLIENT
        Client client = service.save(clientDto);

        // INSERT OWNER
        ownerDto.updateClient(client);
        ownerService.save(ownerDto);

        return "redirect:/client/list";
    }
}
