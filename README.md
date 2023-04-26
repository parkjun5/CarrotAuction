# 당근마켓 x 경매 프로젝트

이 프로젝트의 주 목표는 이펙티브 자바에서 배운 내용을 녹여내고 실무에서 발생할 수 있는 상황을 해결하는 것이다.  
부 목표는 경매와 같은 새로운 기능을 프로젝트에 추가해 나갈 것이다.

---
## Overview

## 프로젝트 목표
1. __당근 마켓의 기존 기능 개발__:
      -	당근마켓의 주요 기능 구현 (경매, 채팅, 도메인 룰적용, 회원, 상품, 글)
      -	이펙티브 자바에서 권장하는 견고하고 유연한 코드 작성
      -	REST API의 규약을 잘 지키며 패키지 구조 DDD
2.	__NonBlocking 채팅 기능 개발__:
    - Webflux의 WebSocet과 NoSql(MongoDB)를 활용 일부 기능을 완전 NonBlocking으로 구성
    - Reactive Programing으로 __이벤트 드라이븐__ 실시간 처리 서버 구현
3.	__분산 환경 구성__:
      -	트래픽을 분산하기 위해 다중 서버 구성
      -	이로 인해 발생될 문제 대비 세션 클러스터링, 동시성 문제 경험
4.	__외부 API 사용__:
      -	외부 API를 사용한 기능 구현
      -	Mock단위 테스트 작성
      -	API의 결과를 저장하여 정기적으로 결과의 타입이 이전과 같은 지 확인하는 절차
5.	__단위 테스트 작성__:
      -	가능하다면 TDD를 사용해 테스트 커버리지가 80퍼센트 이상
6.	__성능 개선__:
      - 성능 테스트를 통해 병목지점 확인 후 개선
      - 트래픽 최저점 대비 100배까지 증가하는 트래픽 스파이크 대비머ㅏ

## 세부 기능
1.	회원
      -	회원 가입
2.	상품
      -	상품 등록
      -	카테고리 등록
      -	상품 상태 변경 (예약, 판매완료, 대기)
3. 채팅
      -	채팅 방 생성
      -	채팅 전달
4. 알림
      -	알림 전달
      -	알림 읽음
5. 경매기능
      -	방 생성
      -	채팅
      -	금액 설정
      -	값 제안
      -	즉시구매 제안

### 예상 클래스 다이어그램
<img width="535" alt="클래스다이어그램" src="https://user-images.githubusercontent.com/58926619/220302198-71772e17-cabd-4a5e-aead-894bf6e2d250.png">


        예상 경매장 1안
        1. 경매 -> 맥북 50만원 (0/ 10) 6시반 시작
        2. 정해진 짧은 경매시간동안 (ex 30분) 100만 1원 5% -> 채팅 -> 1초 동시성... 등등 처리 필요
        예상 경매장 2 안
        1. 경매 포스트 등록 -> 최대 한달 
        2. 한달 동안 유저들이 상품을 보고 금액을 등록
        3. 경매기간이 끝나면 마지막 등록된 사람과 거래
        

## 기술 스펙
### Spring boot, Gradle, Java 17, Kotlin  
### Spring Data JPA, Spring Reactive Programing, Spring WebFlux  
### Redis MongoDb atlas
### Docker  
