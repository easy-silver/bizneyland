package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.web.dto.MemberCreateRequestDto;
import com.bizns.bizneyland.web.dto.MemberUpdateRequestDto;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository repository;
    private final CompanyRepository companyRepository;

    /**
     * 전체 회원 목록
     * @return 전체 회원 리스트
     */
    public List<MemberResponseDto> findAllDesc() {
        return repository.findAllWithCompany()
                .stream()
                // 회원번호 0보다 큰 데이터만 변환
                .filter((m) -> m.getId() > 0)
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 회원 상세
     * @return 단일 회원
     */
    public MemberResponseDto findOne(Long id) {
        Member entity = repository.findOneWithCompany(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        return new MemberResponseDto(entity);
    }

    /**
     * 회원 등록
     * @param requestDto
     * @return 회원번호
     */
    @Transactional
    public Long save(MemberCreateRequestDto requestDto) {
        // 회사 조회
        Company company = companyRepository.findById(requestDto.getCompanySeq())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회사입니다. 회사번호: " + requestDto.getCompanySeq()));
        // DTO > Entity 변환
        Member member = requestDto.toEntity();
        // 연관 엔티티(회사) 등록
        member.changeCompany(company);
        // 회원 등록 후 회원번호 반환
        return repository.save(member).getId();
    }

    /**
     * 회원 수정
     * @param requestDto
     * @return 회원번호
     */
    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        member.update(requestDto.getNickname(),
                requestDto.getBirth(),
                requestDto.getGender(),
                requestDto.getMobile(),
                requestDto.getGrade(),
                requestDto.getWorkingArea(),
                requestDto.getEmail(),
                requestDto.getFax(),
                requestDto.getProfileFileSeq());

        return id;
    }

    /**
     * 회원 삭제(PK)
     * @param id
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * USER.USER_SEQ로 회원 단건 조회
     * @param userSeq
     * @return 단일 회원
     */
    public MemberResponseDto findByUserSeq(Long userSeq) {
        Member entity = repository.findByUserSeq(userSeq);

        return entity == null ? null : new MemberResponseDto(entity);
    }

    /**
     * 회사 소속 회원 전체 조회
     * @param companySeq
     * @return 소속 회원들
     */
    public List<MemberResponseDto> findByCompanySeq(Long companySeq) {
        return repository.findByCompanySeq(companySeq)
                .stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

}
