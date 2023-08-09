## vet_2023

> develop vet repository (2021)  
> 동물병원 예약 기능 위주 구현  
> API overview : http://localhost:8080/docs/overview.html

---
### System  
* **client** : 동물병원 관리자  
* **admin** : 시스템 관리자

---
### Specification

* **Backend** : Spring boot  + Spring security + JPA + PostgreSQL  
* **Frontend(document)** : Spring Rest Docs

---
### domains

* clinic
* employee (proto)
* animal
* guardian
* reservation
* reservation management
* user (proto)

---
### feature

* **VET**
  - [x] 등록
  - [x] 단건 조회
  - [x] 다건 조회 
  - [x] 수정
  - [x] 삭제   
<br/>

* **animal**
  - [x] 등록
  - [x] 단건 조회
  - [x] 다건 조회
  - [x] 검색
  - [x] 수정
  - [x] 삭제  
<br/>

* **guardian**
  - [x] 등록
  - [x] 단건 조회
  - [x] 수정
  - [x] 삭제  
<br/>

* **reservation**
  - [x] 예약 가능 시간 조회
  - [x] 등록
  - [x] 단건 조회
  - [x] 다건 조회
  - [x] 검색
  - [x] 수정   
<br/>

* **reservation management**
  - [x] 조회
  - [x] 등록
  - [x] 수정
 
