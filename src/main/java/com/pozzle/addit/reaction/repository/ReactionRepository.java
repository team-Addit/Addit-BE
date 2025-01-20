package com.pozzle.addit.reaction.repository;

import com.pozzle.addit.reaction.entity.Reaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByAuthorIdAndTickleId(Long authorId, Long tickleId);
}
