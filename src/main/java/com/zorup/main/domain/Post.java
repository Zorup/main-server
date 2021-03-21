package com.zorup.main.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)  //차후 생성시간, 수정 시간은 상위 개쳬로 뺄것
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="POST_TB")
public class Post implements Serializable {

    private static final long serialVersionUID = 3160597931495509333L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long postId;

    @Column
    private String content;

    /*
    @Column
    private Long userId; 차후 유저 테이블 추가후 Foreign Key로 걸 것
    * */

    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    private Long groupId;

    @Column(columnDefinition = "integer default 0")
    private Integer likes;

    /*
    * 이미지 소스 or 파일 소스 저장관련 column (blob 또는 S3사용시 varchar 형식 주소 사용)
    * */
}
