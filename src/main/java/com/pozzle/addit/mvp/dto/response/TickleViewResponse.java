package com.pozzle.addit.mvp.dto.response;

import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.tickle.entity.Tickle;

public record TickleViewResponse(
    String tickleId,
    String tickleDescription,
    int tickleLikes,
    String authorId,
    String authorNickname,
    String authorImage
) {

  public static TickleViewResponse of(Tickle tickle, MvpUser user) {
    return new TickleViewResponse(
        tickle.getUuid(),
        tickle.getDescription(),
        tickle.getReactionsCount(),
        user.getUuid(),
        user.getNickname(),
        user.getImage()
    );
  }
}
