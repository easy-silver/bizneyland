package com.bizns.bizneyland.domain.company;

import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.domain.owner.Owner;
import com.bizns.bizneyland.domain.owner.OwnerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyRepositoryTest {

    @Autowired CompanyRepository repository;
    @Autowired MemberRepository memberRepository;
    @Autowired OwnerRepository ownerRepository;
    @Autowired EntityManager em;

    private Company createCompany() {
        return repository.save(Company
                .builder()
                .name("테스트")
                .businessNo("123-45-78900")
                .build());
    }

    private Member createMember(Company company) {
        return memberRepository.save(Member.builder()
                .userSeq(999L)
                .name("테스트 멤버")
                .company(company)
                .build());
    }

    private Owner createOwner(Company company, Member member) {
        return ownerRepository.save(Owner.builder()
                .company(company)
                .member(member)
                .name("나대표")
                .build());
    }

    @Test
    public void 회사저장_불러오기() {
        //given
        Company company = createCompany();

        //when
        List<Company> companies = repository.findAll();

        //then
        Company findCompany = companies.get(0);
        Assertions.assertThat(findCompany).isEqualTo(company);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 3, 22, 0,0,0);
        createCompany();

        //when
        List<Company> companies = repository.findAll();

        //then
        Company company = companies.get(0);
        System.out.println(">>>>>>>>> createDate=" + company.getCreatedDate()
                + ", modifiedDate=" + company.getModifiedDate());

        assertThat(company.getCreatedDate()).isAfter(now);
        assertThat(company.getModifiedDate()).isAfter(now);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 회사_삭제() {
        //given
        Company company = createCompany();

        //when
        repository.deleteById(company.getId());
        repository.findById(company.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 존재하지 않습니다."));
    }

    @Test
    public void 사업자번호로_조회() {
        Company company = createCompany();

        //when
        Company findCompany = repository.findByBusinessNo(company.getBusinessNo());
        //Company findCompany = repository.findByBusinessNo("123");

        //then
        assertThat(company).isEqualTo(findCompany);
        //assertThat(findCompany).isNull();
    }

    @Test
    public void 회사번호_사업자번호로_조회() {
        //given
        Company company = createCompany();
        Long seq = company.getId();
        String businessNo = company.getBusinessNo().substring(0, 3);

        //when
        boolean isExist = repository.checkValidCompany(seq, businessNo);

        //then
        assertThat(isExist).isTrue();
    }

    @Test
    public void 회사_수정() {
        //given
        Company company = createCompany();
        String modifiedAddress = "제주도 제주시 제주도 가고싶다면";

        //when
        company.update(modifiedAddress, company.getTel(), company.getLogoFileSeq());

        //then
        Company modifiedCompany = repository.findById(company.getId()).get();
        assertThat(modifiedCompany.getAddress()).isEqualTo(modifiedAddress);
    }

    @Test
    public void 대표자_정보_포함_회사_목록() {
        //given
        Company company = createCompany();
        Member member = createMember(company);
        Owner owner = createOwner(company, member);

        // DB INSERT 위해 flush
        em.flush();
        em.clear();

        //when
        List<Company> companies = repository.findAllWithOwner();

        //then
        assertThat(companies.size()).isEqualTo(1);
        assertThat(companies.get(0).getOwner().getName()).isEqualTo(owner.getName());
    }

}