package com.example.socialpost.domain;

import com.example.socialpost.common.entity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="COMMENT_TB")
public class Comment extends TimeEntity implements Serializable {
    private static final long serialVersionUID = -6685459028399937763L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user; //덧글 작성

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    @OnDelete(action = OnDeleteAction.CASCADE)  // delete all comments related to the post when delete the post
    @JsonIgnore
    private Post post; //덧글 작성

    @Column
    private String content; //실제 덧글 내용

    @Data
    @Transactional
    public static class CommentResponse{
        private Long commentId;
        private String content;
        private String userName;
        private String createdDate;
        private String modifiedDate;

        public CommentResponse(Comment c){
            User u = c.getUser();
            this.commentId = c.getCommentId();
            this.content = c.getContent();
            this.userName = u.getName();
            this.createdDate = c.getCreatedDate();
            this.modifiedDate = c.getModifiedDate();
        }
    }

}
