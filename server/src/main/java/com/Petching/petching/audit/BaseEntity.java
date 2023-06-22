package com.Petching.petching.audit;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public abstract class BaseEntity {
    @CreatedDate
    @Column(name= "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.modifiedAt = now;
    }
    @PreUpdate
    public void onPreUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
