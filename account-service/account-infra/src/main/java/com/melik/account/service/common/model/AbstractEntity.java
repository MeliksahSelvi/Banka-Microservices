package com.melik.account.service.common.model;

import com.melik.account.service.common.valueobject.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 20)
    private StatusType statusType = StatusType.ACTIVE;
}
