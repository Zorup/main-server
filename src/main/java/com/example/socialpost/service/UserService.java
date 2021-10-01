package com.example.socialpost.service;

import com.example.socialpost.common.exception.AlreadyExitIdException;
import com.example.socialpost.common.exception.HUserNotFoundException;
import com.example.socialpost.common.security.JwtTokenProvider;
import com.example.socialpost.domain.Role;
import com.example.socialpost.domain.User;
import com.example.socialpost.repository.UserJpaRepo;
import com.example.socialpost.repository.UserProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        return userJpaRepo.findByUserId(userId).orElseThrow(AlreadyExitIdException::new);
    }

    public List<User.UserMentionResponse> getAllUserInForum(){
        List<User> userList = userJpaRepo.findAll();
        List<User.UserMentionResponse> mentionTargets = new ArrayList<>();
        for(User user : userList){
            User.UserMentionResponse userMentionResponse = new User.UserMentionResponse(user);
            mentionTargets.add(userMentionResponse);
        }
        return mentionTargets; // 차후 기능 확장시 그룹id로 조회해야될 가능성 존재
    }

    public List<UserProjection> getPushTokenByUserId(Long[] userId){
        return userJpaRepo.findPushTokenByUserIdIn(userId);
    }

    public void setUserPushToken(Long userId, String token){
        User user = userJpaRepo.findById(userId).orElseThrow(HUserNotFoundException::new);
        user.setPushToken(token);
    }
}
