package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.util.FormatUtil;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import com.bizns.bizneyland.web.dto.CompanyResponseDto;
import com.bizns.bizneyland.web.dto.CompanyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompanyService {

    private final CompanyRepository repository;

    /**
     * 전체 회사 조회
     * @return 회사 리스트
     */
    public List<CompanyResponseDto> findAllDesc() {
        return repository.findAllWithOwner()
                .stream()
                .filter(company -> company.getId() > 0)
                .map(CompanyResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 단일 회사 조회
     * @param id
     * @return
     */
    public CompanyResponseDto findById(Long id) {
        Company entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다. id=" + id));

        return new CompanyResponseDto(entity);
    }

    /**
     * 회사 등록
     * */
    @Transactional
    public Long save(CompanyRequestDto requestDto) {
        // 전화번호 하이픈 제거
        requestDto.setTel(FormatUtil.removeHyphen(requestDto.getTel()));

        return repository.save(requestDto.toEntity()).getId();
    }

    /**
     * 회사 정보 수정
     * @param id
     * @param requestDto
     * @return
     */
    @Transactional
    public Long update(Long id, CompanyUpdateRequestDto requestDto) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다. id=" + id));
        company.update(requestDto.getAddress(), requestDto.getTel(), requestDto.getLogoFileSeq());

        return id;
    }

    /**
     * 회사 삭제
     * @param id
     */
    public void delete(Long id) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다. id=" + id));
        repository.delete(company);
    }

    /**
     * 회사 등록여부 조회
     * @param businessNo
     */
    public boolean isExist(String businessNo) {
        Company entity = repository.findByBusinessNo(businessNo);
        return entity != null;
    }

    /**
     * 회원가입 시 유효한 회사인지 확인
     * @return 존재 여부
     */
    public boolean isValidCompany(Long id, String businessNo) {
        return repository.checkValidCompany(id, businessNo);
    }
}
