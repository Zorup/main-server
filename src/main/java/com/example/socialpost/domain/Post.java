package com.example.socialpost.domain;

import com.example.socialpost.common.entity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
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

    @Column
    private String content;

    @ManyToOne(targetEntity = User.class, fetch=FetchType.LAZY)
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user; //단방향 관계

    @Column
    private Long groupId;

    @Column(columnDefinition = "integer default 0")
    private Integer likes;

    //게시판명 추가
}
