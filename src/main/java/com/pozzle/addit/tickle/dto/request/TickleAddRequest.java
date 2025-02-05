package com.pozzle.addit.tickle.dto.request;

public record TickleAddRequest(
    String relayId,
    String tickleDescription,
    String userImage,
    String userName
) {

}
