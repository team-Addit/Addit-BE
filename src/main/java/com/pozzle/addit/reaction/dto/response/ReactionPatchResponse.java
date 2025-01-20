package com.pozzle.addit.reaction.dto.response;

import com.pozzle.addit.reaction.entity.ReactionType;

public record ReactionPatchResponse(
    String message,
    ReactionType reactionType
) {

}
