package com.bizns.bizneyland.web.restcontroller;

import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.MemberCreateRequestDto;
import com.bizns.bizneyland.web.dto.MemberRequestDto;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/member")
    public Long save(@RequestBody MemberCreateRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @PutMapping("/member/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberRequestDto requestDto) {
        return memberService.update(id, requestDto);
    }

    @GetMapping("/member/{id}")
    public MemberResponseDto findById(@PathVariable Long id) {
        return memberService.findOne(id);
    }
}
