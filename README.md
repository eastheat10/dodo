## 요구사항

### 회원관리

* 등록된 사용자는 ID/PW 인증 및 oauth 인증으로 로그인 할 수 있습니다.
* 회원가입은 회원 가입 버튼을 클릭해서 진행합니다.
    * 회원가입시 계정정보(id,email,password)를 입력하여 저장합니다.
* [github.com](http://github.com/)의 이메일과 동일한 경우, [github.com](http://github.com/)의 oauth 인증으로도 로그인 가능합니다.

### 프로젝트

* 사용자는 Project 를 생성할 수 있습니다.
    * Project를 생성한 사용자는 Project 의 관리자 입니다.
    * Project는 프로젝트 이름, 상태(활성, 휴면, 종료) 를 가집니다.
* Project 관리자는 멤버를 등록할 수 있습니다.
    * Project 멤버는 회원관리에서 가입한 회원만 가능합니다.
* Project 멤버는 Task 를 등록, 수정, 삭제 할 수 있습니다.
* Project 멤버는 Task 의 목록 및 내용을 확인 할 수 있습니다.
* Project 멤버는 Project 에 Tag, MileStone 을 등록, 수정, 삭제 할 수 있습니다.
* Project 에 등록한 Tag, MileStone 을 Task에 설정 할 수 있습니다.
    * Tag : Task 에 설정할 속성입니다. Task에 1개이상의 Tag 를 설정할 수 있습니다.
    * MileStone : Project 진척 상황을 나타내는 이정표입니다. Task 에 한개의 MileStone 을 설정할 수 있습니다.
* Project 멤버는 Task 에 Comment 를 생성할 수 있습니다.
* Comment 를 생성한 사용자는 Comment 를 수정, 삭제 할 수 있습니다.
* Task-Api, Project, Task, Comment 의 구조는 다음과 같습니다.

![image1](https://user-images.githubusercontent.com/38150034/176182534-be3224fe-6dc3-4623-9da0-3ce24fbbcaa9.png)
<br>
## 설계

![image2](https://user-images.githubusercontent.com/38150034/176182609-ec7ac22d-294a-4517-943a-e25a297f8333.png)
<br>
* **gateway 는 모든 서비스 요청을 받으며 프레젠테이션 기능을 담당합니다**.
    * TemplateEngine(<b><span style="color:#e11d21">Thymeleaf</span></b>) 사용하여 화면을 표시합니다.
    * 데이터는 AccountApi, TaskApi 를 <b><span style="color:#e11d21">RestTempate</span></b>으로 호출하여 받아 옵니다.
    * 화면정보를 표시할때 AccountApi, TaskApi 를 조합해서 제공할 수 있어야 합니다.
* gateway 는 사용자의 인증을 담당합니다.
    * 인증 세션은 gateway 서버에서 redis 를 사용하여 관리합니다.
        * RedisTemplate 사용
    * 인증 데이터는 Account-Api 를 사용합니다.
* AccountApi 는 멤버의 정보를 관리합니다.
* ProjectApi 는 Project, Task, Comment, Tag 를 관리 합니다.

### Account-api

* 인증처리는 GateWay 에 위임합니다.
* (RestApi)회원 정보를 제공합니다.
* (RestApi)회원의 상태(가입,탈퇴,휴면)를 관리(cud)합니다.

### Task-api

* 인증처리는 GateWay 에 위임합니다.
* (RestApi) Project, Task, Comment, Tag, MileStone 정보를 관리하는 API 를 제공합니다.
