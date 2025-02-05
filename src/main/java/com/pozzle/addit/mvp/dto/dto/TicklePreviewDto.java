package com.pozzle.addit.mvp.dto.dto;

import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.tickle.entity.Tickle;

public record TicklePreviewDto(
    String tickleId,
    String tickleImage,
    String authorImage,
    String authorNickname
) {

  public static TicklePreviewDto of(Tickle tickle, MvpUser user) {
    return new TicklePreviewDto(
        tickle.getUuid(),
        tickle.getFile(),
        user.getImage(),
        user.getNickname()
    );
  }
}
