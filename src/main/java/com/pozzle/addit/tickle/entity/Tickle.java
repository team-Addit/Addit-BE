package com.pozzle.addit.tickle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "tickles")
@EntityListeners(AuditingEntityListener.class)
public class Tickle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long relayId;

    private Long authorId;

    @Column(length = 36, nullable = false, unique = true)
    private String uuid;

    @Column(length = 300, nullable = false)
    private String description;

    @Lob
    private String file;

    @CreatedDate
    private LocalDateTime createdAt;

    private int repliesCount;

    private int reactionsCount;

    private int funnyCount;

    private int sadCount;

    private int surpriseCount;

    private int likeCount;
}
