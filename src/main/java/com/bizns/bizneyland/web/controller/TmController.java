package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.OwnerService;
import com.bizns.bizneyland.web.dto.ClientRequestDto;
import com.bizns.bizneyland.web.dto.OwnerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/tm")
@Controller
public class TmController {

    private final ClientService clientService;
    private final OwnerService ownerService;

    @GetMapping("form")
    public String clientForm() {
        return "tm/registerClient";
    }

    @PostMapping("registerClient")
    public String registerClient(ClientRequestDto clientDto, OwnerRequestDto ownerDto) {

        // INSERT CLIENT
        Client client = clientService.save(clientDto);

        // INSERT OWNER
        ownerDto.updateClient(client);
        Long ownerSeq = ownerService.save(ownerDto);

        System.out.println("clientSeq = " + client.getClientSeq());
        System.out.println("ownerSeq = " + ownerSeq);


        return "redirect:/tm/form/tmInfo";
    }

    @GetMapping("form/tmInfo")
    public String tmForm() {
        return "tm/registerTmInfo";
    }

    @GetMapping("form/tm/sales")
    public String salesForm() {
        return "tm/registerSales";
    }
}
