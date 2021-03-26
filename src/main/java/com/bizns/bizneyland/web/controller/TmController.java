package com.bizns.bizneyland.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tm")
@Controller
public class TmController {

    @GetMapping("form")
    public String form() {
        return "tm/register";
    }
}
