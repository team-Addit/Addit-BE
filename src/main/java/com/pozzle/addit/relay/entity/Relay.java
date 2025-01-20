package com.pozzle.addit.relay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "relays")
@EntityListeners(AuditingEntityListener.class)
public class Relay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    @Column(length = 36, nullable = false, unique = true)
    private String uuid;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String description;

    private int reactionsCount;

    private int ticklesCount;

    @Enumerated(EnumType.STRING)
    private RelayStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void addTickle() {
        this.ticklesCount++;
        this.updatedAt = LocalDateTime.now();
    }
}
