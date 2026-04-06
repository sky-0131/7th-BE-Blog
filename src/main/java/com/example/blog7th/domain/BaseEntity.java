package com.example.blog7th.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // 1. 생성 시간
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 2. 수정 시간
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // 3. 삭제 여부
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // 4. 삭제 시간
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public void delete(LocalDateTime deletedAt) { // 외부에서 시간을 주입받음
        this.isDeleted = true;
        this.deletedAt = deletedAt;
    }

    public void undoDelete() {
        this.isDeleted = false;
        this.deletedAt = null;
    }
}