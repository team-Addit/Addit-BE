package com.pozzle.addit.reaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reactions")
@EntityListeners(AuditingEntityListener.class)
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tickleId;

    private Long authorId;

    @Enumerated(EnumType.STRING)
    private ReactionType type;

    public boolean isSameType(ReactionType reactionType) {
        return this.type.equals(reactionType);
    }

    public void changeType(ReactionType reactionType) {
        this.type = reactionType;
    }
}
