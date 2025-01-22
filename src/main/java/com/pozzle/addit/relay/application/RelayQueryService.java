package com.pozzle.addit.relay.application;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.relay.dto.response.RelayInfoResponse;
import com.pozzle.addit.relay.dto.response.TickleIdsResponse;
import com.pozzle.addit.relay.dto.response.TickleThumbnail;
import com.pozzle.addit.relay.dto.response.TickleThumbnailsResponse;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.relay.entity.RelayTag;
import com.pozzle.addit.relay.entity.Tag;
import com.pozzle.addit.relay.repository.RelayRepository;
import com.pozzle.addit.relay.repository.RelayTagRepository;
import com.pozzle.addit.relay.repository.TagRepository;
import com.pozzle.addit.tickle.entity.Tickle;
import com.pozzle.addit.tickle.repository.TickleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RelayQueryService {

    private final RelayRepository relayRepository;
    private final RelayTagRepository relayTagRepository;
    private final TagRepository tagRepository;
    private final TickleRepository tickleRepository;


    public RelayInfoResponse readRelayInfo(String relayId) {
        boolean pinned = false;

        Relay relay = relayRepository.findByUuid(relayId)
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));

        List<String> tagNames = relayTagRepository.findAllByRelayId(relay.getId()).stream()
            .map(RelayTag::getTagId)
            .map(tagRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Tag::getName)
            .collect(Collectors.toList());

        return RelayInfoResponse.of(relay, tagNames, pinned);
    }

    public TickleIdsResponse readTickleIds(String relayId) {
        Relay relay = relayRepository.findByUuid(relayId)
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));

        List<Tickle> tickles = tickleRepository.findAllByRelayId(relay.getId());

        return TickleIdsResponse.of(tickles);
    }

    public TickleThumbnailsResponse getAroundThumbnails(String tickleId, int items) {
        Tickle targetTickle = tickleRepository.findByUuid(tickleId)
            .orElseThrow(() -> new RestApiException(ErrorCode.TICKLE_NOT_FOUND));
        Relay relay = relayRepository.findById(targetTickle.getRelayId())
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));
        List<Tickle> tickles = tickleRepository.findAllByRelayId(relay.getId());

        int targetIndex = tickles.indexOf(targetTickle);
        int startIndex = Math.max(0, targetIndex - items);
        int endIndex = Math.min(tickles.size(), targetIndex + items + 1);
        List<Tickle> result = tickles.subList(startIndex, endIndex);

        List<TickleThumbnail> thumbnails = result.stream()
            .map(TickleThumbnail::of)
            .collect(Collectors.toList());
        return TickleThumbnailsResponse.of(thumbnails);
    }
}
