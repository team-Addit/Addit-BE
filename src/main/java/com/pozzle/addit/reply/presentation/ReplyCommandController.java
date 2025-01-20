package com.pozzle.addit.reply.presentation;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.reply.application.ReplyCommandService;
import com.pozzle.addit.reply.dto.ReplyCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyCommandController {

    private final ReplyCommandService replyCommandService;

    @PostMapping(value = "/")
    @Operation(summary = "티클에 댓글 작성",
        description = "티클에 댓글을 작성합니다."
    )
    public ResponseEntity<?> createReply(
        @RequestBody ReplyCreateRequest request
    ) {
        replyCommandService.createReply(request);
        return Response.ok("success add reply", null);
    }

}
