package com.example.socialpost.domain;

import com.example.socialpost.common.entity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//게시글 테이블
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="POST_TB")
public class Post extends TimeEntity implements Serializable {
    private static final long serialVersionUID = 3160597931495509333L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long postId;

    @Column(length = 500)
    private String content;

    @ManyToOne(targetEntity = User.class, fetch=FetchType.LAZY)
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user; //단방향 관계

    @Column
    private Long groupId;

    @Column(columnDefinition = "integer default 0")
    private Integer likes;

    @ManyToOne(targetEntity = Forum.class, fetch=FetchType.LAZY)
    @JoinColumn(name="forumId")
    @JsonIgnore
    private Forum forum;

    /*
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();*/

    @Data
    @Transactional
    public static class PostRequest{
        private String content; // innerHTML with <img src="">
        private Long forumId;
        private Long groupId;
    }

    @Data
    @Transactional
    public static class PostResponse{
        private Integer likes;
        private Long postId;
        private Long userId;
        private Long forumId;
        private Long groupId;
        private List<Comment.CommentResponse> comments;
        private List<Image.ImageResponse> images;
        private String content;
        private String userName;
        private String createdDate;
        private String modifiedDate;

        public PostResponse(Post p){
            User u = p.getUser();
            this.likes = p.getLikes();
            this.postId = p.getPostId();
            this.userId = u.getUserId();
            this.forumId = p.getForum().getForumId();
            this.groupId = p.getGroupId();
            this.comments = new ArrayList<>();
            this.images = new ArrayList<>();
            this.content = p.getContent();
            this.userName = u.getName();
            this.createdDate = p.getCreatedDate();
            this.modifiedDate = p .getModifiedDate();
        }
    }
}
