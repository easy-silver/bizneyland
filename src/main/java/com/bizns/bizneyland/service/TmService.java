package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.loan.Loan;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.domain.tm.Tm;
import com.bizns.bizneyland.domain.tm.TmRepository;
import com.bizns.bizneyland.web.dto.LoanRequestDto;
import com.bizns.bizneyland.web.dto.TmCreateRequestDto;
import com.bizns.bizneyland.web.dto.TmResponseDto;
import com.bizns.bizneyland.web.dto.TmUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TmService {
    private final TmRepository repository;
    private final ClientRepository clientRepository;
    private final MemberRepository memberRepository;
    private final LoanService loanService;

    /**
     * TM 상담 등록
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(TmCreateRequestDto requestDto) {

        // 고객(Client) 조회
        Client client = clientRepository.findById(requestDto.getClientSeq())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다. id=" + requestDto.getClientSeq()));

        // 상담사(Member) 조회
        Member member = memberRepository.findByUserSeq(requestDto.getUserSeq());

        // TM 등록
        Tm tm = requestDto.toEntity().updateClient(client).updateCaller(member);
        Tm savedTm = repository.save(tm);

        // 대출 정보 등록
        loanService.save(toLoanList(requestDto.getLoan(), savedTm));

        return savedTm.getTmSeq();
    }

    /**
     * 대출 정보 리스트 생성
     */
    private List<Loan> toLoanList(LoanRequestDto dto, Tm tm) {
        List<Loan> list = new ArrayList<>();

        if (dto != null) {
            String[] creditors = dto.getCreditors();
            int[] amounts = dto.getAmounts();
            String[] purposes = dto.getPurposes();
            String[] memos = dto.getMemos();

            int repeatCount = Math.min(creditors.length, amounts.length);

            for (int i = 0; i < repeatCount; i++) {
                if (creditors[i].isEmpty())
                    continue;

                Loan entity = Loan.builder()
                        .tm(tm)
                        .creditor(creditors[i])
                        .amount(amounts[i])
                        .build();

                if (purposes != null && purposes.length > i)
                    entity.setPurpose(purposes[i]);

                if (memos != null && memos.length > i)
                    entity.setMemo(memos[i]);

                list.add(entity);
            }
        }

        return list;
    }

    /**
     * 전체 상담건 조회
     */
    public List<TmResponseDto> findAllDesc() {
        return repository.findAllDesc()
                .stream()
                .map(TmResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 고객번호로 상담건 조회
     * @param clientSeq
     * @return tmList
     */
    public List<TmResponseDto> findByClient(Long clientSeq) {
        return repository.findByClient(clientSeq)
                .stream()
                .map(TmResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 상담번호로 한 건 조회
     */
    public TmResponseDto findById(Long tmSeq) {
        Tm entity = repository.findById(tmSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 상담 건이 없습니다. tmSeq=" + tmSeq));
        return new TmResponseDto(entity);
    }

    /**
     * 상담 내용 수정
     * @param requestDto
     * @return
     */
    public Long updateTmInfo(TmUpdateRequestDto requestDto) {
        Tm tm = repository.findById(requestDto.getTmSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 상담 건이 없습니다. SEQ=" + requestDto.getTmSeq()));

        tm.updateTmInfo(requestDto);

        return tm.getTmSeq();
    }

    /**
     * 한 건 삭제
     * @param seq(PK)
     */
    public void delete(Long seq) {
        repository.deleteById(seq);
    }
}
