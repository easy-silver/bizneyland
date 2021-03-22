package com.bizns.springboot.web.controller;

import com.bizns.springboot.service.CompanyService;
import com.bizns.springboot.web.dto.CompanyRequestDto;
import com.bizns.springboot.web.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CompanyApiController {

    private final CompanyService service;

    @PostMapping("/company")
    public Long save(@RequestBody CompanyRequestDto requestDto) {
        return service.save(requestDto);
    }

    @PutMapping("/company/{id}")
    public Long update(@PathVariable Long id, @RequestBody CompanyRequestDto requestDto) {
        return service.update(id, requestDto);
    }

    @GetMapping("/company/{id}")
    public CompanyResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
