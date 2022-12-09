# memo-restapi

#### 1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
- 수정은 id, (제목, 이름, 비밀번호, 내용)여러 키 값이 필요한데 비밀번호를 노출하면 안되기 때문에 PostRequestDto를 body에 담아서 보내기 위해 RequsetBody를 사용했다
- 삭제는 제목 이름 내용 필요없이 id, 비밀번호만 있으면 되기 때문에 비밀번호를 RequestParam으로 body에 담아서 보내주었다

#### 2. 어떤 상황에 어떤 방식의 request를 써야하나요?
- 데이터의 양이 많지 않고 서버에서 데이터를 가공하지 않고 클라이언트에게 그대로 보내주면 될 때 GET
- 데이터를 서버로 보내고 새리소스를 만들 때, 정보를 숨겨야 할 때 POST
- 기존 리소스를 업데이트 할 때, 변경 할 속성 뿐 아니라 모든 속성의 데이터 필요 PUT
- 기존 리소스를 수정하는 것은 PUT 방식과 유사하지만 변경하려는 특정 속성만 포함되면 된다 PATCH
- 지정된 리소스를 삭제하려 할 때 DELETE


#### 3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
REST란 URI를 통해 자원을 표시하고 HTTP Method를 이용하여 데이터를 전송 및 처리하는 방법
- REST 기반으로 CRUD 의도에 맞게 api 서비스 구현, 제공


#### 4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
- Controller는 웹에서 요청이 왔을 때 클라이언트에서 받은 데이터를 적절한 Service로 넘겨주고 처리한 후 지정된 뷰에 다시 객체를 넘겨주는 역할
- Service는 알맞는 비즈니스 로직을 수행하고 가공하여 Controller에게 다시 데이터를 넘긴다. DB정보가 필요할 때 Repository에 요청
- Repository는 Entity객체에 의해 DB에 직접 접근, 관리, CRUD를 수행한다


#### 5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!

| Method | URL | Request | Response                                                                                                                                                                                                                                                                                              |
| --- | --- | --- |-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET | /api/posts | - | {<br/>{"id": 1,<br/>"title": "title1",<br/>"name": "name1",<br/>"content": "content1",<br/>"createdAt": "2022-12-08T12:00:01.123456”<br/> }, <br/>{<br/>"id": 2,<br/>"title": "title2",<br/>"name": "name2",<br/>"content": "content2",<br/>"createdAt": "2022-12-08T12:01:01.123456” }<br/> … <br/>} | 
| GET | /api/post/{id} | - | {<br/>"id": 1,<br/>"title": "title1",<br/>"name": "name1",<br/>"content": "content1",<br/>"createdAt": "2022-12-08T12:00:01.123456”<br/>}                                                                                                                                                             |
| POST | /api/post | {<br/>"title" : "title1",<br/>"name" : "name1",<br/>"password" : "password1",<br/>"content" : "content1"<br/>} | {<br/>"id": 1,<br/>"title": "title1",<br/>"name": "name1",<br/>"content": "content1",<br/>"createdAt": "2022-12-08T12:00:01.123456”<br/>}                                                                                                                                                             |
| PUT | /api/post/{id} | {<br/>"title" : "title1",<br/>"name" : "name1",<br/>"password" : "password1",<br/>"content" : "content12345"<br/>} | {<br/>"title" : "title1",<br/>"name" : "name1",<br/>"password" : "password1",<br/>"content" : "content12345",<br/>"createdAt": "2022-12-08T12:00:01.123456”<br/>}                                                                                                                                                    |
| DELETE | /api/post/{id} | {<br/>"password" :"password1"<br/>} | {<br/>"success": true<br/>}                                                                                                                                                                                                                                                                           |