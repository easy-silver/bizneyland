package com.bizns.bizneyland.web.restcontroller;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.service.CompanyService;
import com.bizns.bizneyland.web.dto.ClientResponseDto;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import com.bizns.bizneyland.web.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CompanyApiController {

    private final CompanyService service;
    private final CompanyRepository repository;

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

    /**
     * 사업자 번호로 등록 여부 확인
     * @param businessNo
     * @return
     */
    @GetMapping("/company/find/{businessNo}")
    public Map<String, Object> findByBusinessNo(@PathVariable String businessNo) {
        Company company = repository.findByBusinessNo(businessNo);

        Map<String, Object> map = new HashMap<>();
        boolean isExist = company != null;
        map.put("isExist", isExist);

        if (isExist)
            map.put("company", new CompanyResponseDto(company));

        return map;
    }

}
