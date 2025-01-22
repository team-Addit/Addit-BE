package com.pozzle.addit.relay.application;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.relay.dto.event.RelayDeletedEvent;
import com.pozzle.addit.relay.dto.request.RelayCreateRequest;
import com.pozzle.addit.relay.dto.request.RelayUpdateRequest;
import com.pozzle.addit.relay.dto.response.RelayCreateResponse;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.relay.entity.RelayStatus;
import com.pozzle.addit.relay.entity.RelayTag;
import com.pozzle.addit.relay.entity.Tag;
import com.pozzle.addit.relay.repository.RelayRepository;
import com.pozzle.addit.relay.repository.RelayTagRepository;
import com.pozzle.addit.relay.repository.TagRepository;
import com.pozzle.addit.tickle.entity.Tickle;
import com.pozzle.addit.tickle.repository.TickleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class RelayCommandService {

    private final RelayRepository relayRepository;
    private final TickleRepository tickleRepository;
    private final RelayTagRepository relayTagRepository;
    private final TagRepository tagRepository;
    private final ApplicationEventPublisher eventPublisher;

    public RelayCreateResponse createRelay(RelayCreateRequest request, MultipartFile file) {
        //TODO file 변환 및 주소값 가져오기
        Long authorId = 1L;
        String fileUrl = "file url";

        Relay relay = Relay.builder()
            .authorId(authorId)
            .uuid(UUID.randomUUID().toString())
            .title(request.title())
            .description(request.relayDescription())
            .reactionsCount(0)
            .ticklesCount(1)
            .status(RelayStatus.ACTIVE)
            .updatedAt(LocalDateTime.now())
            .build();
        relayRepository.save(relay);

        Tickle tickle = Tickle.builder()
            .relayId(relay.getId())
            .authorId(authorId)
            .uuid(UUID.randomUUID().toString())
            .description(request.tickleDescription())
            .file(fileUrl)
            .build();
        tickleRepository.save(tickle);

        assignTagWithRelay(relay, request.tags());

        return new RelayCreateResponse(relay.getUuid(), tickle.getUuid());
    }

    private void assignTagWithRelay(Relay relay, List<String> tags) {
        tags.forEach(t -> {
            Tag tag = tagRepository.findByName(t)
                .orElseGet(() -> tagRepository.save(
                        Tag.builder()
                            .name(t)
                            .build()
                    )
                );
            relayTagRepository.save(
                RelayTag.builder()
                    .relayId(relay.getId())
                    .tagId(tag.getId())
                    .build()
            );
        });
    }

    public String updateRelay(RelayUpdateRequest request) {
        Relay relay = relayRepository.findByUuid(request.relayId())
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));
        relay.update(request);

        relayTagRepository.deleteAllByRelayId(relay.getId());
        assignTagWithRelay(relay, request.tags());

        return request.relayId();
    }

    public void deleteRelay(String relayId) {
        Relay relay = relayRepository.findByUuid(relayId)
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));
        relayRepository.delete(relay);
        relayTagRepository.deleteAllByRelayId(relay.getId());

        eventPublisher.publishEvent(new RelayDeletedEvent(relay.getId()));
    }
}
