package com.example.socialpost.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FORUM_TB")
public class Forum implements Serializable {
    private static final long serialVersionUID = 7928566405512173638L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumId;

    @Column
    private String forumName;

    //차후 그룹 추가시 그룹명 추가
}
