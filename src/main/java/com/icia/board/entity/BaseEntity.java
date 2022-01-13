package com.icia.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
// 내 자식들이 뭘하고 있는지 감지하는 설정
@EntityListeners(AuditingEntityListener.class)
@Getter
//abstract => 추상클래스
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime; //insert 수행한 시간

    @UpdateTimestamp
    @Column(insertable = false) //insert 할때는 들어가지 않는다.
    private LocalDateTime updateTime; // update 수행한 시간
}
