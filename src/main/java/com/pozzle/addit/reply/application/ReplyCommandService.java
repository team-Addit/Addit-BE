package com.pozzle.addit.reply.application;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.reply.dto.ReplyCreateRequest;
import com.pozzle.addit.reply.entity.Reply;
import com.pozzle.addit.reply.repository.ReplyRepository;
import com.pozzle.addit.tickle.entity.Tickle;
import com.pozzle.addit.tickle.repository.TickleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyCommandService {

    private final TickleRepository tickleRepository;
    private final ReplyRepository replyRepository;

    public Long createReply(ReplyCreateRequest request) {
        Long authorId = 1L;

        Tickle tickle = tickleRepository.findByUuid(request.tickleId())
            .orElseThrow(() -> new RestApiException(ErrorCode.TICKLE_NOT_FOUND));

        tickle.addReply();

        Reply reply = Reply.builder()
            .tickleId(tickle.getId())
            .authorId(authorId)
            .content(request.content())
            .build();
        replyRepository.save(reply);

        return reply.getId();
    }
}
