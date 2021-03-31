package com.example.socialpost.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

//좋아요 테이블
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LIKE_TB")
public class Like implements Serializable {
    private static final long serialVersionUID = 947527756355329152L;
    @EmbeddedId
    private LikePK compositeKey;
}
