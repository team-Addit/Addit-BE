package com.pozzle.addit.relay.application;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.relay.dto.response.RelayInfoResponse;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.relay.entity.RelayTag;
import com.pozzle.addit.relay.entity.Tag;
import com.pozzle.addit.relay.repository.RelayRepository;
import com.pozzle.addit.relay.repository.RelayTagRepository;
import com.pozzle.addit.relay.repository.TagRepository;
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
}
