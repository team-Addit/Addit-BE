package com.pozzle.addit.relay.dto.response;

import com.pozzle.addit.tickle.entity.Tickle;

public record TickleThumbnail(
    String tickleId,
    String thumbnailUrl
) {

    public static TickleThumbnail of(Tickle tickle) {
        return new TickleThumbnail(
            tickle.getUuid(),
            tickle.getFile()
        );
    }

}
