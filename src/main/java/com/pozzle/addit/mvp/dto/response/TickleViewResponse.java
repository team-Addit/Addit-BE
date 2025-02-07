package com.pozzle.addit.mvp.dto.response;

import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.tickle.entity.Tickle;
import java.util.List;

public record TickleViewResponse(
    String relayId,
    String relayTitle,
    List<String> tags,
    String tickleId,
    String tickleDescription,
    String tickleImage,
    int tickleLikes,
    String authorId,
    String authorNickname,
    String authorImage
) {

    public static TickleViewResponse of(Relay relay, List<String> tags, Tickle tickle,
        MvpUser user) {
        return new TickleViewResponse(
            relay.getUuid(),
            relay.getTitle(),
            tags,
            tickle.getUuid(),
            tickle.getDescription(),
            tickle.getFile(),
            tickle.getReactionsCount(),
            user.getUuid(),
            user.getNickname(),
            user.getImage()
        );
    }
}
