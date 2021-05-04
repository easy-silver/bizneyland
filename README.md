# Biz & System - 상담관리 시스템

## 프로젝트 설명
고객 및 고객에 대한 상담 내용을 관리하는 시스템입니다.

## 사용한 기술
* Spring Boot 2.1.9
* JPA(Spring Data JPA)
* JUnit 4
* Thymeleaf
* AWS(EC2, RDS)
* OAuth2.0

## 프로그램 화면
<img width="800" alt="스크린샷 2021-05-04 오전 9 45 58" src="https://user-images.githubusercontent.com/42424276/116949790-2e91c180-acbe-11eb-8726-d0062d999dff.png">
<img width="800" alt="스크린샷 2021-05-04 오전 9 49 57" src="https://user-images.githubusercontent.com/42424276/116949821-4701dc00-acbe-11eb-9300-672fed9bbe58.png">

## 테이블 구조
<img width="800" alt="스크린샷 2021-05-04 오전 10 02 10" src="https://user-images.githubusercontent.com/42424276/116950388-d491fb80-acbf-11eb-8677-f5eaf8614889.png">

## 핵심 엔티티 및 연관관계
- User: 계정 사용자   
구글 로그인 시 생성되는 엔티티로 구글 계정과 관련된 정보를 가진다.   
Member의 외래키를 포함하며 Member와 OneToOne 연관관계를 갖는다.

- Member: 소속회원(직원)   
구글 로그인 후 회원등록 시 생성되는 엔티티이다.   
등급은 ADMIN(관리자), OWNER(회사대표), USER(상담사), GUEST(비회원) 네 가지로 나뉜다.   
OWNER는 자신의 회사가 담당하는 고객만 관리할 수 있다.   
USER는 자신이 상담한 고객만 관리할 수 있다.

- Client: 고객   
상담의 대상이며 하나의 고객에 여러 개의 상담(TM)이 존재할 수 있다.

- Sales: 매출   
매출 정보는 고객에게 종속된다.   
하나의 고객은 여러 건의 매출 정보를 가질 수 있다.   
매출 기준으로 ManyToOne 관계를 갖는다.

- TM: 상담   
반드시 하나의 고객이 지정되어야 한다.   
상담을 기준으로 고객은 ManyToOne 관계를 갖는다.

