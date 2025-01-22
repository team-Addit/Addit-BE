package com.pozzle.addit.relay.dto.response;

import com.pozzle.addit.tickle.entity.Tickle;
import java.util.List;

public record TickleIdsResponse(
    List<String> tickleIds
) {
    public static TickleIdsResponse of(List<Tickle> tickles) {
        return new TickleIdsResponse(
            tickles.stream()
                .map(Tickle::getUuid)
                .toList()
        );
    }
}
