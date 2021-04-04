package com.example.socialpost.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass   //해당 어노테이션 사용시 해당 클래스를 상속했을 때, 상속받은 엔티티가 해당 엔티티의 column을 인식
@EntityListeners(AuditingEntityListener.class) //JPA Auditing 엔티티에서 반복적으로 사용되는 값을 감시하여 자동으로 넣어준다.
public abstract class TimeEntity {
    private String createdDate;
    private String modifiedDate;
    /*@CreatedDate
    private LocalDateTime createdDate; //해당 엔티티가 생성될 때 값이 자동 저장
    @LastModifiedDate
    private LocalDateTime modifiedDate; //해당 엔티티가 수정될 때 값이 자동 저장*/

    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
