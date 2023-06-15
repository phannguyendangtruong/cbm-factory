package com.amitgroup.sqldatabase.entities;

import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.OffsetDateTime;
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    public Long id;
    @Column(name = "created_by")
    public Long createdBy;
    @Column(name = "created_at")
    public OffsetDateTime createdAt = OffsetDateTime.now();
    @Column(name = "modified_by")
    public Long modifiedBy;
    @LastModifiedDate
    @Column(name = "modified_at")
    public OffsetDateTime modifiedAt;
    @Column(name = "is_deleted")
    public Boolean isDeleted = false;
    @Column(name = "deleted_at")
    public OffsetDateTime deletedAt;
    @Column(name = "deleted_by")
    public Long deletedBy;
}
