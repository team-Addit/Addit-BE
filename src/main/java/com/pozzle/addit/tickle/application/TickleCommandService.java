package com.pozzle.addit.tickle.application;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.relay.dto.event.RelayDeletedEvent;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.relay.repository.RelayRepository;
import com.pozzle.addit.tickle.dto.event.TickleDeletedEvent;
import com.pozzle.addit.tickle.dto.request.TickleAddRequest;
import com.pozzle.addit.tickle.dto.response.TickleAddResponse;
import com.pozzle.addit.tickle.entity.Tickle;
import com.pozzle.addit.tickle.repository.TickleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class TickleCommandService {

    private final RelayRepository relayRepository;
    private final TickleRepository tickleRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TickleAddResponse addTickle(TickleAddRequest request, MultipartFile file) {
        //TODO file 변환 및 주소값 가져오기
        Long authorId = 1L;
        String fileUrl = "file url";

        Relay relay = relayRepository.findByUuid(request.relayId())
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));

        relay.addTickle();

        Tickle tickle = Tickle.builder()
            .relayId(relay.getId())
            .authorId(authorId)
            .uuid(UUID.randomUUID().toString())
            .description(request.tickleDescription())
            .file(fileUrl)
            .build();
        tickleRepository.save(tickle);

        return new TickleAddResponse(relay.getUuid(), tickle.getUuid());
    }

    @EventListener
    public void onRelayDeletedEvent(RelayDeletedEvent event) {
        List<Tickle> tickles = tickleRepository.findAllByRelayId(event.relayId());
        List<Long> ids = tickles.stream()
            .map(Tickle::getId)
            .toList();

        tickleRepository.deleteAll(tickles);
        ids.forEach(id -> eventPublisher.publishEvent(new TickleDeletedEvent(id)));
    }
}
