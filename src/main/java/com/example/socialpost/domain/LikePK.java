package com.example.socialpost.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class LikePK implements Serializable {
    private static final long serialVersionUID = -3824039740859236323L;

    @OneToOne(targetEntity = User.class, fetch= FetchType.LAZY)
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user; // 각 좋아요는 하나의 사용자에 의해 입력

    @OneToOne(targetEntity = Post.class, fetch=FetchType.LAZY)
    @JoinColumn(name="postId")
    @OnDelete(action = OnDeleteAction.CASCADE)  // delete all like infos related to the post when delete the post
    @JsonIgnore
    private Post post; // 각 좋아요는 하나의 게시글에 대해서 맵핑
}
