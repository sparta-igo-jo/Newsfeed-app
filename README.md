## 📝 프로젝트 소개

- Sparta NewsFeed 프로젝트
- 프로젝트명 : NewsFeed
- 내용 : 유저 프로필, 사용자 인증, 게시물, 팔로우 및 좋아요, 댓글을 활용한 뉴스피드 프로젝트

<br>


## 💁‍♂️ 프로젝트 팀원

|리더|팀원|팀원|팀원|팀원|
|:---:|:---:|:---:|:---:|:---:|
|[이규정](https://github.com/KyujungLee)|[박용준](https://github.com/dereck-jun)|[전영준](https://github.com/lamgak12)|[석연걸](https://github.com/SeokYeongeol)|[조은종](https://github.com/Roloya28)|

<br />

## 🧑‍💻 팀 역할
| 역할 | 이규정| 박용준 | 전영준 |석연걸|조은종|
|:---:|:---:|:---:|:---:|:---:|:---:|
| 담당 기능 | 뉴스피드 게시물 관리 |프로필 관리|친구 관리|사용자 인증|댓글 관리|

<br>


## 🛠️ 와이어 프레임

![image](https://github.com/user-attachments/assets/b6d4cb3d-0f80-400a-86ac-b3ec5a6dac06)


<br>

## 🗂️ APIs

나중에 추가

<br />

## 🖥 ERD 다이어그램 

![image](https://github.com/user-attachments/assets/7803b2fc-fcb5-4955-a9be-0d12a3babbae)

<br>

## 🛠️ Tech Stacks
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Java.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringBoot.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringDataJPA.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Mysql.png?raw=true" width="80">
</div>

<br>

## 🗣️기술적 의사결정

<details>
  <summary>DataBase : MySQL</summary>
<br>

- 관계를 맺고 있는 데이터가 자주 수정되는 경우, MySQL의 관계형 데이터 모델과 트랜잭션 관리 기능은 데이터의 무결성과 일관성을 보장하는 데 유리합니다.

<br>
</details>





<br>

## 🤔 Trouble Shoothing

<details>
  <summary>양방향 참조 문제</summary>

<br>

📢 WasteRecord 와 WasteItem 간의 양방향 참조 문제

➡️ 오류 내용


- WasteRecord는 다시 WasteItem 리스트를 참조하면서 무한 순환 참조가 발생하여 JSON 직렬화 시 깊이 제한을 초과하는 문제가 발생

2024-10-27T19:17:39.304+09:00 WARN 24252 --- [ecogrow-backend][nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Ignoring exception, response committed already: org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Document nesting depth (1001) exceeds the maximum allowed (1000, from StreamWriteConstraints.getMaxNestingDepth())

2024-10-27T19:17:39.304+09:00 WARN 24252 --- [ecogrow-backend][nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Document nesting depth (1001) exceeds the maximum allowed (1000, from StreamWriteConstraints.getMaxNestingDepth())]



➡️ 원인 :

- 양방향 탐색
 
  - WasteRecord를 직렬화할 때 직렬 변환기는 wasteItems를 포함한 모든 필드를 포함하게 됨.
    
  - wasteItems의 각 WasteItem에 대해 직렬 변환기는 WasteRecord를 다시 참조하는 wasteRecord를 포함한 모든 필드를 포함하게 됨.

  - 즉 WasteRecord -> WasteItem -> WasteRecord -> WasteItem -> (무한히 계속).


➡️ 해결 방법 :

- @JsonIgnoreProperties 사용

- 주석으로 이 체인을 끊음으로 JSON 직렬화 중 순환 참조를 방지하는 동시에 Java 코드에서 양방향 관계를 그대로 유지

@JsonIgnoreProperties("wasteRecord")
private List<WasteItem> wasteItems = new ArrayList<>();


@JsonManagedReference 및 @JsonBackReference: 이는 JSON에서 관리되는 부모-자식 관계를 생성하여 "부모" 측(WasteRecord를 WasteItem으로)만 직렬화하고 "하위 항목에서 재귀를 방지합니다. " 측면(WasteItem에서 WasteRecord까지).


<br>

</details>
<details>
  <summary>Spring Boot의 모호한 핸들러 매핑</summary>

<br>

📢 Spring Boot의 모호한 핸들러 매핑

- 쓰레기 기록 메인 페이지에서 인증된 특정 사용자의 특정 게시글을 클릭하면 특정 데이터를 가지고 있는 쓰레기 기록 상세 페이지를 조회하는데 실패하는 상황


- ➡️ 오류 내용

2024-10-29T20:30:10.827+09:00 ERROR 628 --- [ecogrow-backend][nio-8080-exec-8] o.a.c.c.C.[.[.[/].[dispatcherServlet] : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/api/waste/record/3': {public org.springframework.http.ResponseEntity com.sw.ecogrowbackend.domain.waste.controller.WasteRecordController.getWasteRecords(java.lang.Long), public org.springframework.http.ResponseEntity com.sw.ecogrowbackend.domain.waste.controller.WasteRecordController.getWasteRecord(java.lang.Long)}] with root cause java.lang.IllegalStateException: Ambiguous handler methods mapped for '/api/waste/record/3': {public org.springframework.http.ResponseEntity com.sw.ecogrowbackend.domain.waste.controller.WasteRecordController.getWasteRecords(java.lang.Long), public org.springframework.http.ResponseEntity com.sw.ecogrowbackend.domain.waste.controller.WasteRecordController.getWasteRecord(java.lang.Long)} at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.lookupHandlerMethod(AbstractHandlerMethodMapping.java:431) ~[spring-webmvc-6.1.12.jar:6.1.12] at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:382) ~[spring-webmvc-6.1.12.jar:6.1.12] at org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.getHandlerInternal(RequestMappingInfoHandlerMapping.java:127) ~[spring-webmvc-6.1.12.jar:6.1.12] at org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.getHandlerInternal(RequestMappingInfoHandlerMapping.java:68) ~[spring-webmvc-6.1.12.jar:6.1.12] at org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler(AbstractHandlerMapping.java:507) ~[spring-webmvc-6.1.12.jar:6.1.12] at


➡️ 원인 :

- 쓰레기 기록에 관한 controller 에서 동일한 경로 /api/waste/record/{id}에 매핑된 두 개의 핸들러 메서드가 있기 때문.

- getWasteRecord(@PathVariable Long RecordId): recordId로 단일 폐기물 기록을 가져오기 위해 /api/waste/record/{recordId}에 매핑

- getWasteRecords(@PathVariable Long userId): userId를 기반으로 특정 사용자에 대한 모든 레코드를 가져오기 위한 /api/waste/record/{userId}에도 매핑

- 두 메소드 모두 동일한 기본 경로(/api/waste/record/{id})를 사용하기 때문에 Spring은 경로 변수를 사용하여 이 경로에 요청이 이루어질 때 사용할 핸들러 메소드를 결정할 수 없으며 '모호한 핸들러'가 발생.

➡️ 해결 방법 :

- 문제를 해결하기 위해 하나의 경로를 변경하면 됨.

- '기록' 이라는 용어는 개별 항목과 더 밀접하게 연관되어 있으므로 단일 기록 검색을 /api/waste/record/{recordId}
에 유지하고 사용자별 기록을 '/api/waste/record/user/{userId}로 최신화 하는 것이 좋음

<br>
</details>

<details>
  <summary>특정 페이지 조회 시 Hibernate 무한 루프 아슈</summary>
<br>
📢 프론트에서 쓰레기 기록 메인 페이지 조회 시 서버 콘솔에서 Hibernate 무한 루프 발생

➡️ 오류 내용 

Hibernate: select p1_0.id, p1_0.bio, p1_0.created_at, p1_0.modified_at, p1_0.profile_image_url, u1_0.id, u1_0.approval_status, u1_0.created_at, u1_0.email, u1_0.google_id, u1_0.kakao_id, u1_0.modified_at, u1_0.password, u1_0.resigned_at, u1_0.role, u1_0.username from profiles p1_0 left join users u1_0 on u1_0.id=p1_0.user_id where p1_0.user_id=? Hibernate: / select count(wasteRecord) from WasteRecord wasteRecord / select count(wr1_0.id) from waste_records wr1_0 Hibernate: / select count(wasteRecord) from WasteRecord wasteRecord / select count(wr1_0.id) from waste_records wr1_0 Hibernate: / select wasteRecord from WasteRecord wasteRecord order by wasteRecord.createdAt desc / select wr1_0.id, wr1_0.created_at, wr1_0.modified_at, wr1_0.user_id from waste_records wr1_0 order by wr1_0.created_at desc limit ?, ? Hibernate: / select wasteRecord from WasteRecord wasteRecord order by wasteRecord.createdAt desc / select wr1_0.id, wr1_0.created_at, wr1_0.modified_at,


➡️ 원인 :

- 종속성 배열에 'fetchAllRecords'를 포함하면 구성요소가 다시 렌더링될 때마다 'useEffect'가 다시 트리거되어 지속적인 API 호출 루프가 발생



➡️ 해결 방법 :

- 'fetchAllRecords'를 한 번만 호출하도록 'useEffect'를 조정하고 'fetchData'를 'currentPage'에만 종속되게 수정

<br>
</details>
