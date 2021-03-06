package com.example.socialpost.service;

import com.example.socialpost.common.exception.AlreadyExitIdException;
import com.example.socialpost.common.exception.HUserNotFoundException;
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
        log.info("signIn Service..");

        if(userJpaRepo.findByLoginId(param.getLoginId()).isPresent()){
            throw new AlreadyExitIdException();
        }

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
                .orElseThrow(HUserNotFoundException::new);

        if(!passwordEncoder.matches(r.getPassword(),user.getPassword())){
            throw new HUserNotFoundException();
        }
        log.info("Login Success");
        return jwtTokenProvider.createToken(user.getUsername(),user.getRole());
    }

    public User.UserResponse getUserInfo(Long userId){
        User user = userJpaRepo.findById(userId).orElseThrow(HUserNotFoundException::new);
        return (new User.UserResponse(user));
    }

    //그룹, 채팅, 알람의 경우 차후 해당 엔티티와 서비스에서 구현? or User service에서 처리
    public User.UserResponse modifyUserInfo(User.SignRequest r){
        User user = userJpaRepo.findByLoginId(r.getLoginId()).get();

        if(r.getPassword()!=null){
            r.setPassword(passwordEncoder.encode(r.getPassword()));
        }
        user.modifyUserMember(r);
        return new User.UserResponse(userJpaRepo.save(user));
    }

    public User getInfoBytoken(String token){
        String s = jwtTokenProvider.getUserPk(token);
        return userJpaRepo.findByLoginId(s).orElseThrow(AlreadyExitIdException::new);
    }
}
