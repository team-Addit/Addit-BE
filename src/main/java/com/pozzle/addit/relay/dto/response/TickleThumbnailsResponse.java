package com.pozzle.addit.relay.dto.response;

import java.util.List;

public record TickleThumbnailsResponse(
    List<TickleThumbnail> tickleThumbnails
) {

    public static TickleThumbnailsResponse of(List<TickleThumbnail> thumbnails) {
        return new TickleThumbnailsResponse(thumbnails);
    }

}
