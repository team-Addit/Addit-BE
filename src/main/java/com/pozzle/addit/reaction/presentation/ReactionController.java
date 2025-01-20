package com.pozzle.addit.reaction.presentation;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.reaction.application.ReactionCommandService;
import com.pozzle.addit.reaction.dto.response.ReactionPatchResponse;
import com.pozzle.addit.reaction.entity.ReactionType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reactions")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionCommandService reactionCommandService;

    @PatchMapping(value = "/{tickleId}/{reaction}")
    @Operation(summary = "반응 추가",
        description = "티클에 반응을 추가합니다."
    )
    public ResponseEntity<?> patchReaction(
        @PathVariable String tickleId,
        @PathVariable ReactionType reaction
    ) {
        ReactionPatchResponse response = reactionCommandService.patchReaction(tickleId, reaction);
        return Response.ok(response.message(), response.reactionType());
    }

}
