# CyProject
싸이월드 프로젝트

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
    * [기본적인 게시물 CRUD](#기본적인-crud)
    * [AJAX / Rest API](#ajax-/-rest-api)
    * [댓글과 대댓글 구현](#댓글과-대댓글-구현)
    * [Transaction](#transaction)
    * [Spring Scheduler](#spring-scheduler)
    * [노트북 추천 시스템](#노트북-추천-시스템)
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
- Spring Boot jpa
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

## 역할 분담

김동규

서유영

손주영

유언수

홍수아
