package com.example.socialpost.domain;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;

//유저 정보 테이블
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_TB")
public class User implements Serializable {
    private static final long serialVersionUID = 300058225713026175L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column
    private String name;

    @Column
    private String loginId; //unique?

    @Column
    private String password;

    @Column
    private String position; //직책

    @Column
    private String department; //소속

    @Column
    private String email;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    //DTO 객체의 경우 inner 클래스로 구현
    @Data
    @Transactional
    public static class LoginRequest{
        @ApiParam(value = "아이디", required = true)
        private String loginId;
        @ApiParam(value = "비밀번호", required = true)
        private String password;
    }

    @Data
    @Transactional
    public static class SignRequest{
        @ApiParam(value = "회원 이름", required = false)
        private String name;
        @ApiParam(value = "로그인 시 사용될 아이디", required = false)
        private String loginId;
        @ApiParam(value = "비밀번호", required = false)
        private String password;
        @ApiParam(value = "직책", required = false)
        private String position;
        @ApiParam(value = "소속", required = false)
        private String department;
        @ApiParam(value = "이메일", required = false)
        private String email;
        @ApiParam(value = "프로필 이미지", required = false)
        private byte[] image;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Transactional
    public static class UserResponse{
        private Long pk;
        private String name;
        private String loginId;
        private String position;
        private String department;
        private String email;
        private byte[] image;

        public UserResponse(User user){
            pk = user.getUserId();
            name = user.getName();
            loginId = user.getLoginId();
            position = user.getPosition();
            department = user.getDepartment();
            email = user.getEmail();
            image = user.getImage();
        }
    }

    public void modifyUserMember(User.SignRequest r){
        if(r.getPassword()!=null) password = r.getPassword(); //이 부분 서비스단에서 암호화해서 set해서 던져주기
        if(r.getEmail()!=null) email = r.getEmail();
        if(r.getPosition()!=null) position = r.getPosition();
        if(r.getDepartment()!=null) department = r.getDepartment();
        if(r.getImage()!=null) image = r.getImage();
        if(r.getName()!=null) name = r.getName();
    }
}
