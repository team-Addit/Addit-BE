package com.pozzle.addit.relay.dto.response;

import com.pozzle.addit.relay.entity.Relay;
import java.util.List;

public record RelayInfoResponse(
    String relayTitle,
    String description,
    List<String> tags,
    boolean pinned
) {

    public static RelayInfoResponse of(Relay relay, List<String> tags, boolean pinned) {
        return new RelayInfoResponse(
            relay.getTitle(),
            relay.getDescription(),
            tags,
            pinned
        );
    }
}
