# main-server


<br/>
<br/>
<br/>

> ## 로컬개발시의 도커 컨테이너 이용




서버가 분화되고 많아지면서 Intellij로 모든 서버를 동작시켰더니 
컴퓨터가 안좋아서 너무 느리고 오류도 많이 납니다.

때문에 당장 개발중인 서버 외에 다른 서버들은 도커로 다소 가볍고 편하게 띄워놓을수 있도록 Dockerfile을 구성했습니다.

열심히 다 만들어놓고 테스트하고 나서야, 그냥 도커 없어도 호스트머신에서 터미널키고 서버 구동시키면 된다는걸 깨달았습니다...

그래도 한번 켜놓으면 stop, start 명령어로 간편하게 껐다켰다 할 수 있음에 의의를 둬봅시다...
<br/>
<br/>


### **1. application.yml 확인**

application.yml 파일 내의 url, db이름 등이 본인 환경에 맞게 되어있는지 확인합니다.

사실 다른부분은 신경쓸것 없고 on-profile: local-docker 로 된 부분에서 포트번호랑 db이름/비밀번호 정도만 확인하면 됩니다.
<br/>
<br/>


### **2. 프로젝트 빌드**

실행하려는 서버 프로젝트의 루트 디렉토리로 들어가 프로젝트를 빌드해 jar파일을 생성합니다.
```
./graldew build -x test
```
<br/>

### **3. 도커 이미지 생성**

작성되어있는 Dockerfile을 이용해 원하는 서버의 도커 이미지를 생성합니다.
```
docker build -t 이미지이름 ./

ex)
// main 서버의 경우
docker build -t mainimg

// gateway 서버의 경우
docker build -t gateimg

// static 서버의 경우
docker build -t statimg

// chat 서버의 경우
docker build -t chatimg
```
<br/>

### **4. 도커 컨테이너 생성**

생성한 이미지로 컨테이너를 생성합니다.
```
// main 서버의 경우
docker run -d -p 8082:8082 --name main mainimg

// gateway 서버의 경우
docker run -d -p 8081:8081 --name gate gateimg

// static 서버의 경우
docker run -d -p 8080:8080 --name stat statimg

// chat 서버의 경우
docker run -d -p 8083:8083 --name chat chatimg
```
<br/>

### **5. 로그 확인**
```
docker logs 컨테이너이름
```
위 명령어로 서버가 잘 시작되었는지 로그 확인 후 사용하면 됩니다.

그 외에도 서버 로그 확인이 필요할 경우 위 명령어를 사용합니다.
<br/>
<br/>

### **6. 기타**
서버를 사용하지 않을땐 아래 명령어로 컨테이너를 중지시켜둘 수 있습니다.
```
docker stop 컨테이너이름
```
다시 서버를 사용하게되면 아래 명령어로 컨테이너를 가동시킵니다.
```
docker start 컨테이너이름
```
<br/>
<br/>



> ## 참고자료

1. [Spring Data JPA Projections](https://www.baeldung.com/spring-data-jpa-projections).

JPA 사용시 엔티티에서 특정 Column만 프로젝션하여 사용해야하는 경우가 있습니다. 


    public List<User.UserMentionResponse> getAllUserInForum(){
	    List<User> userList = userJpaRepo.findAll();
	    List<User.UserMentionResponse> mentionTargets = new ArrayList<>();
	    for(User user : userList){
		    User.UserMentionResponse userMentionResponse = new User.UserMentionResponse(user);
		    mentionTargets.add(userMentionResponse);
	    }
	    return mentionTargets; 
    }

위 링크의 내용처럼 Class Based projection은 JPA만을 사용하여 컬렉션형태로 반환하기 어렵습니다. 따라서 기존의 코드는 쿼리 자체에서 특정 column을 프로젝션 하지 않고,
DTO 클래스를 작성한 후 일일히 값을 넣어주는 방식으로 작성하였습니다.

찾아보니 다음과 같이 Interface 기반의 Projection 방법이 있어 내용 공유합니다.

    package com.example.socialpost.repository;
    
    public interface UserProjection {
	    Long getUserId();
	    String getPushToken();
    }

다음과 같이 Projection하고자하는 column에 대한 getter를 인터페이스를 통해 작성합니다. 

    @Query("select u.userId as userId, u.pushToken as pushToken from User u where u.userId in :userIds")  
    List<UserProjection> findPushTokenByUserIdIn(@Param("userIds")Long[] userIds);

이후 다음과 같이 쿼리 작성 후 위에 작성한 인터페이스를 사용하여 값을 반환하면 
Getter에 작성한 아이디 : value값으로 파싱된 형태의 json형태를 얻을 수 있습니다.
사용시 다음의 내용을 주의해야합니다. 

Note that  **recursive projections only work if we traverse from the owning side to the inverse side.**  Were we to do it the other way around, the nested projection would be set to  _null_.

추가적으로 실제 쿼리에서 프로젝션하는 필드명과 인터페이스에서 작성한 getter의 이름이 다르면 Null값으로 출력됩니다. 따라서 위 코드와 같이 column명에 alias를 주어 getter와 동일하게 맞춘 후 사용하는 것이 좋겠습니다. 

---
