package com.example.socialpost.domain;

import com.example.socialpost.common.entity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

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
    @JsonIgnore
    private Post post; //덧글 작성

    @Column
    private String content; //실제 덧글 내용

}
