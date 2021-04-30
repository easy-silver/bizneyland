package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.config.auth.LoginUser;
import com.bizns.bizneyland.config.auth.dto.SessionUser;
import com.bizns.bizneyland.domain.user.Role;
import com.bizns.bizneyland.service.ClientService;
import com.bizns.bizneyland.service.SalesService;
import com.bizns.bizneyland.service.TmService;
import com.bizns.bizneyland.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void list(@LoginUser SessionUser user, Model model) {
        model.addAttribute("clients",
                user.getRole() == Role.ADMIN ? service.findAll() : service.findAll(user.getCompanySeq()));
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
     * 등록 전 고객 확인 페이지
     */
    @GetMapping("findBeforeRegister")
    public void findBeforeRegister() {

    }

    /**
     * 업체 등록 여부 확인
     * @param parameterMap
     * @return
     */
    @PostMapping("findBeforeRegister")
    @ResponseBody
    public Map<String, Object> findBeforeRegister(@RequestBody Map<String, String> parameterMap) {
        Map<String, Object> responseMap = new HashMap<>();
        String contact = parameterMap.get("contact");
        Long companySeq = Long.parseLong(parameterMap.get("companyCharge"));

        ClientResponseDto client = service.findBeforeRegister(contact, companySeq);

        responseMap.put("isExist", client != null);
        responseMap.put("client", client);

        return responseMap;
    }

    /**
     * 고객 수정 화면
     */
    @GetMapping("update/{seq}")
    public String update(@PathVariable Long seq, Model model) {
        ClientResponseDto client = service.findById(seq);
        List<SalesResponseDto> salesList = salesService.findAllByClient(client.getClientSeq());
        model.addAttribute("client", client);
        model.addAttribute("salesList", salesList);

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

        service.delete(clientSeq);

        return "redirect:/client/list";
    }

    /**
     * TM 상담 추가 등록 화면
     */
    @GetMapping("{clientSeq}/registerTmInfo")
    public String registerTmInfo(@PathVariable Long clientSeq, Model model) {
        ClientResponseDto client = service.findById(clientSeq);
        model.addAttribute("clientName", client.getName());

        TmCreateRequestDto tmDto = new TmCreateRequestDto();
        tmDto.setClientSeq(client.getClientSeq());
        model.addAttribute("tmDto", tmDto);

        return "client/registerTmInfo";
    }

    /**
     * TM 상담 추가 등록
     */
    @PostMapping("{clientSeq}/registerTmInfo")
    public String registerTmInfo(@PathVariable Long clientSeq, TmCreateRequestDto requestDto) {
        tmService.save(requestDto);
        return "redirect:/client/detail/" + clientSeq;
    }
}
