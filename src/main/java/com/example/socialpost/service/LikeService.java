package com.example.socialpost.service;

import com.example.socialpost.domain.Like;
import com.example.socialpost.domain.LikePK;
import com.example.socialpost.domain.Post;
import com.example.socialpost.domain.User;
import com.example.socialpost.repository.LikeJpaRepo;
import com.example.socialpost.repository.PostJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    @Autowired
    private final UserService userService;
    @Autowired
    private final LikeJpaRepo likeJpaRepo;
    @Autowired
    private final PostJpaRepo postJpaRepo;

    public Integer clickLikeEvent(@CookieValue(value = "X-Auth-Token") Cookie cookie, Long postId){
        User u = userService.getInfoBytoken(cookie.getValue());
        Post p = postJpaRepo.findById(postId).get();

        Integer likes = p.getLikes();
        log.info(likes.toString());

        LikePK likePk = new LikePK();
        likePk.setPost(p);
        likePk.setUser(u);

        if(!likeJpaRepo.findById(likePk).isEmpty()){
            decreaseLike(p, likePk);
            likes--;
        }else{
           increaseLike(p,likePk);
           likes++;
        }
        return likes;
    }

    private void increaseLike(Post p, LikePK likePk){
        log.info("log :: increase start");
        Integer like = p.getLikes()+1;
        p.setLikes(like);
        postJpaRepo.save(p);

        Like createLike = Like.builder()
                .compositeKey(likePk).build();
        likeJpaRepo.save(createLike);

        log.info("log :: increase success");
    }

    private void decreaseLike(Post p, LikePK likePk){
        log.info("log :: decrease start");
        Integer like = p.getLikes()-1;
        p.setLikes(like);
        postJpaRepo.save(p);
        likeJpaRepo.deleteById(likePk);

        log.info("log :: decrease success");
    }
}
