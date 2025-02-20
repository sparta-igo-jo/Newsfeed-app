## 📝 프로젝트 소개

- Sparta NewsFeed 프로젝트
- 프로젝트명 : NewsFeed
- 내용 : 유저 프로필, 사용자 인증, 게시물, 팔로우 및 좋아요, 댓글을 활용한 뉴스피드 프로젝트

<br>

## 💁‍♂️ 프로젝트 팀원

|팀장|팀원|팀원|팀원|팀원|
|:---:|:---:|:---:|:---:|:---:|
|[이규정](https://github.com/KyujungLee)|[박용준](https://github.com/dereck-jun)|[전영준](https://github.com/lamgak12)|[석연걸](https://github.com/SeokYeongeol)|[조은종](https://github.com/Roloya28)|

<br />

## 🧑‍💻 팀 역할
| 역할 | 이규정| 박용준 | 전영준 |석연걸|조은종|
|:---:|:---:|:---:|:---:|:---:|:---:|
| 담당 기능 | 뉴스피드 게시물 관리 |프로필 관리|친구 관리|사용자 인증|댓글 관리|

<br>


## 🛠️ 와이어 프레임

![Image](https://github.com/user-attachments/assets/31be4800-09bc-4de5-aec3-f0d472abc8b3)

<br>

## 🖥 ERD 다이어그램

![Image](https://github.com/user-attachments/assets/746a5efb-e52c-4b43-9af2-e675e97c3b02)

<br>

## 🛠️ Tech Stacks
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Java.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringBoot.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringDataJPA.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Mysql.png?raw=true" width="80">
</div>

<br>

## 🤔 Trouble Shoothing

<details>
  <summary>순환참조 문제</summary>

<br>

📢 UserService와 FeedService, CommentService에서 순환참조 발생

➡️ 오류 내용

![Image](https://github.com/user-attachments/assets/93c605b4-dbcb-430c-97a4-ce608c589aff)



➡️ 원인 :

- 순환 참조

  - UserService에서 여러 Service에 의존 하고 있어 발생함


➡️ 해결 방법 :

- 유저가 의존하는 Service를 Read, Write Service로 분리해 의존하게 생성해 해결
