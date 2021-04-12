package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/client")
@Controller
public class ClientController {

    private final ClientService service;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("clients", service.findAll());
        return "client/list";
    }
}
