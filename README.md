# CyProject
싸이월드 프로젝트

[![Video Label](http://img.youtube.com/vi/9ghjtH_Q4n4/0.jpg)](https://youtu.be/9ghjtH_Q4n4)
</br>
<유튜브 영상>

<br>

# 목차
- [개발 환경](#개발-환경)
- [사용 기술](#사용-기술)
    * [백엔드](#백엔드)
    * [프론트엔드](#프론트엔드)
    * [기타 주요 라이브러리](#기타-주요-라이브러리)
- [E-R 다이어그램](#e-r-다이어그램)
- [프로젝트 기획 이유](#프로젝트-기획-이유)
- [핵심 기능](#핵심-기능)
    * [JPA 및 JavaScript 활용](#jpa-활용)
    * [API 활용](#api-활용)
    * [웹소켓 활용](#웹소켓-활용)
    * [스크랩 기능 구현](#스크랩-기능-구현)
- [역할 분담](#역할-분담)

<br>

## 개발 환경
- IntelliJ IDEA
- Postman
- GitHub
- HeidiSQL
- Visual Studio Code

<br>

## 사용 기술
### 백엔드
#### 주요 프레임워크 / 라이브러리
- Java 11
- Spring Boot 2.6.3
- Spring Security
- Spring Boot JPA
- Mybatis


#### Build tool
- Maven

#### Database
- MariaDB

### 프론트엔드
- Javascript
- HTML/CSS
- Thymeleaf

#### 기타 주요 라이브러리
- Lombok
- JSON
- Jsoup
- Apache Commons FileUpload
- Java Mail Sender

<br>

## E-R 다이어그램
![cyworld](https://user-images.githubusercontent.com/83940731/172143415-733cb2e9-276f-4788-abb3-74c929d306f0.png)

<br>

## 프로젝트 기획 이유

어느 날, 이야기를 하다 문득 싸이월드에 대한 이야기가 나온 적이 있습니다.

그런데 생각보다 싸이월드를 한번도 이용해보지 못한 사람들이 꽤 많았고, 그들에게 최대한 설명해보려 했지만 말로는 역시 한계가 있어 직접 보여주고 싶었습니다. 

그런 생각을 하던 중, 중단되었던 싸이월드 서비스를 재개한다는 소식을 들었습니다.

하지만 서비스를 모바일 버전으로만 제공한다는 이야기를 듣고, 소위 말하는 '그 시절 싸이월드 감성'을 잃게 되는 것이 아쉬웠습니다.

그래서 아쉬운 마음과 직접 보여주고 싶은 마음을 안고 이 프로젝트를 기획하게 되었습니다.

<br>

## 핵심 기능
### JPA 활용
JPA 오픈 소스인 Hibernate를 사용했습니다. 객체 관계 매핑(ORM)을 이용해 SQL에 의존적인 개발을 피하고 CRUD 작업, 유저 관리, 검색, 일촌평, 페이징, 장바구니 시스템 등을 구현하였습니다. 그리고 JavaScript를 이용해 AJAX 통신, 정규식, 포인트 충전 기능 등 웹페이지가 좀 더 유연하고 부드럽게 동작할 수 있도록 작업했습니다.

<img width="49%" align="left" alt="main" src="https://user-images.githubusercontent.com/83940731/173243216-d02835e7-4ad7-4967-89db-cad4885cb47d.png"> <img width="49%" align="right" alt="minihome" src="https://user-images.githubusercontent.com/83940731/173243239-2378d573-2003-410f-89d0-7a8996258002.png">
[JPA를 활용한 메인 페이지, 미니홈피]

<br>

### API 활용
아임포트(iamport) API를 이용해 도토리 충전 기능을 구현했습니다. 그리고 카카오 제공 API를 이용해 카카오페이 결제 기능을 작업하고 상품 구매 시스템을 구현했습니다. 또한 메인 페이지 구현에 필요한 데이터를 Jsoup 라이브러리를 사용해 크롤링하였는데, 이때 지도, 뉴스, 웹툰, 영화의 외부 API를 적극적으로 활용하였습니다.

<img width="49%" alt="point" src="https://user-images.githubusercontent.com/83940731/173244284-b1abc463-7128-49eb-ab9d-5845975f4d4e.png"> <img width="49%" align="right" alt="cart" src="https://user-images.githubusercontent.com/83940731/173243317-d1e76956-de5f-4d65-a71e-ec63bed3f330.png">
[포인트 충전 및 장바구니 결제]

<br>

### 웹소켓 활용
온라인 상태인 일촌을 나타내는 기능과 쪽지를 실시간으로 주고 받는 기능을 작업했습니다. 기존 http 단방향통신보다 실시간으로 양방향통신을 하는 웹소켓이 더 적합하다 생각하여 웹소켓 방식을 적용했습니다. 우선 sessionId와 유저 pk값을 Map으로 저장하였습니다. 다른 유저가 로그인했을 때, 해당 유저와 기존에 저장되어 있던 pk들 중 일촌으로 등록된 유저들에게 로그인 표시를 해주었습니다. 마찬가지로 쪽지를 보내는 상대의 sessionId와 매핑되어 있는 유저 pk를 활용해 해당 유저에게 쪽지를 전송, 수신하도록 작업했습니다.

<img width="1792" alt="massage" src="https://user-images.githubusercontent.com/83940731/173244287-a233fb52-5aac-44a5-943d-0f84d2acb928.png">
[웹소켓을 활용한 쪽지 기능]

<br>

<br>

### 스크랩 기능 구현
한 사용자가 여러 게시글을 스크랩하고, 한 게시글이 여러 사용자로부터 스크랩되는 로직을 구현하기 위해 사용자(user)와 게시글(photo-board)의 M:N 관계 (다대다 관계)를 이용해 작업했습니다. 관계형 데이터베이스는 두 테이블 간에 직접적인 다대다 관계를 구현할 수 없습니다. 해당 문제를 해결하기 위해 연결 테이블(조인 테이블)을 추가해 일대다, 다대일 관계로 풀어냈습니다. 연결 테이블 안에 Id(pk)가 2개이기 때문에 Id 클래스를 추가로 생성한 후 Repository에 Id 자료형을 설정하여 작업했습니다.

[Many To One을 활용한 연결 테이블 Entity](https://github.com/CykoProject/CyProject/blob/4ce999d34dc24f2d044f7c15f2f9d544699ad933/src/main/java/com/example/CyProject/home/model/scrap/BoardListEntity.java#L1-L31)

[연결 테이블의 Id Class](https://github.com/CykoProject/CyProject/blob/4ce999d34dc24f2d044f7c15f2f9d544699ad933/src/main/java/com/example/CyProject/home/model/scrap/BoardListId.java#L1-L15)

<br>

## 역할 분담

김동규 (sgrhrg@naver.com) : 마이페이지 담당. 회원 관리 및 메일 인증 기능, 일촌 관리 기능 등

서유영 (403467@naver.com) : 마이페이지 담당. 회원 관리 기능, 포인트 충전 기능 등

손주영 (terranzz01@gmail.com) : 메인 페이지 담당. 검색 기능, 실시간 순위, 상품 구매 기능 등

유언수 (yueonsu@gmail.com) : 미니홈피 담당. 에디터 구현 및 댓글 기능, 쪽지 기능 등

홍수아 (suaah.96@gmail.com) : 미니홈피 담당. 파일 업로드 기능, 스크랩 기능 등
