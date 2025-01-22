package com.pozzle.addit.relay.dto.request;

import java.util.List;

public record RelayUpdateRequest(
    String relayId,
    String title,
    String relayDescription,
    List<String> tags
) {

}
