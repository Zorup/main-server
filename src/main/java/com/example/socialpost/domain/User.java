package com.example.socialpost.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

//유저 정보 테이블
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_TB")
public class User implements Serializable {
    private static final long serialVersionUID = 300058225713026175L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column
    private String name;

    @Column
    private String loginId; //unique?

    @Column
    private String password;

    @Column
    private String position; //직책

    @Column
    private String department; //소속

    @Column
    private String email;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;
}
