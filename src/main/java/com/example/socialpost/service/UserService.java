package com.example.socialpost.service;

import com.example.socialpost.common.security.JwtTokenProvider;
import com.example.socialpost.domain.Role;
import com.example.socialpost.domain.User;
import com.example.socialpost.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public User signIn(User.SignRequest param){
        /*if(userJpaRepo.findByLoginId(param.getLoginId())!=null){
           Id validataion ? 그냥 디비로 유니크키 먹이는 식으로 처리하고 코드상에선 따로 하지말자.
        }*/
        //이미지 입력 안한 경우 default 이미지 들어가도록 처리 추가 아마 이런식으로하려면 스토리지 따로뺴거나?
        //url저장하는식으로 해야할듯.
        if(param.getPosition()!=null){
            param.setPosition("없음");
        }
        if(param.getDepartment()!=null){
            param.setDepartment("없음");
        }

        User user = User.builder()
                .name(param.getName())
                .loginId(param.getLoginId())
                .password(passwordEncoder.encode(param.getPassword()))
                .position(param.getPosition())
                .department(param.getDepartment())
                .email(param.getEmail())
                .image(param.getImage())
                .role(Role.ROLE_USER)
                .build();
        return userJpaRepo.save(user);
    }

    public String login(User.LoginRequest r) throws IllegalArgumentException {
        User user = userJpaRepo.findByLoginId(r.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException(r.getLoginId()+"는 존재하지 않는 아이디 입니다."));

        if(!passwordEncoder.matches(r.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        log.info("Login Success");
        return jwtTokenProvider.createToken(user.getUsername(),user.getRole());
    }

    public User.UserResponse getUserInfo(Long userId){
        //로그인 자체에  이미 어느정도 내용이 포함, 차후 사용 안될 경우 해당 메소드 삭제할 것
        User user = userJpaRepo.findById(userId).get(); //차후 exception 만들면 orElse 등으로 수정할 것
        return (new User.UserResponse(user));
    }

    //그룹, 채팅, 알람의 경우 차후 해당 엔티티와 서비스에서 구현? or User service에서 처리
    public User.UserResponse modifyUserInfo(User.SignRequest r){
        User user = userJpaRepo.findByLoginId(r.getLoginId()).get();
        //r에서 패스워드 입력시 암호화해서 r.setpassword()해서 적용해야함
        if(r.getPassword()!=null){
            r.setPassword(passwordEncoder.encode(r.getPassword()));
        }
        user.modifyUserMember(r);
        return new User.UserResponse(userJpaRepo.save(user));
    }

}
