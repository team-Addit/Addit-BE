package com.pozzle.addit.relay.dto.request;

import java.util.List;

public record RelayCreateRequest(
    String title,
    List<String> tags,
    String relayDescription,
    String tickleDescription
    ) {

}
