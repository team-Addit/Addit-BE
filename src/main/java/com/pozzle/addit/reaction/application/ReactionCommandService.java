package com.pozzle.addit.reaction.application;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.reaction.dto.response.ReactionPatchResponse;
import com.pozzle.addit.reaction.entity.Reaction;
import com.pozzle.addit.reaction.entity.ReactionType;
import com.pozzle.addit.reaction.repository.ReactionRepository;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.relay.repository.RelayRepository;
import com.pozzle.addit.tickle.entity.Tickle;
import com.pozzle.addit.tickle.repository.TickleRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReactionCommandService {

    private final RelayRepository relayRepository;
    private final TickleRepository tickleRepository;
    private final ReactionRepository reactionRepository;

    public ReactionPatchResponse patchReaction(String tickleId, ReactionType reactionType) {
        Long authorId = 1L;

        Tickle tickle = tickleRepository.findByUuid(tickleId)
            .orElseThrow(() -> new RestApiException(ErrorCode.TICKLE_NOT_FOUND));
        Relay relay = relayRepository.findById(tickle.getRelayId())
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));
        Optional<Reaction> reactionOptional = reactionRepository.findByAuthorIdAndTickleId(authorId,
            tickle.getId());

        if (reactionOptional.isPresent()) {
            Reaction reaction = reactionOptional.get();
            if (reaction.isSameType(reactionType)) {
                tickle.removeReaction(reactionType);
                relay.removeReaction();
                reactionRepository.delete(reaction);
                return new ReactionPatchResponse("success remove reaction", reaction.getType());
            } else {
                tickle.removeReaction(reaction.getType());
                tickle.addReaction(reactionType);
                relay.removeReaction();
                relay.addReaction();
                reaction.changeType(reactionType);
                return new ReactionPatchResponse("succes change reaction", reaction.getType());
            }
        }

        tickle.addReaction(reactionType);
        relay.addReaction();

        Reaction reaction = Reaction.builder()
            .tickleId(tickle.getId())
            .authorId(authorId)
            .type(reactionType)
            .build();
        reactionRepository.save(reaction);

        return new ReactionPatchResponse("success add reaction", reaction.getType());
    }
}
