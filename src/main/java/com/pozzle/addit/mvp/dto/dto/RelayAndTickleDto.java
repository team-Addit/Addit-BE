package com.pozzle.addit.mvp.dto.dto;

import java.util.List;

public record RelayAndTickleDto(
    RelayPreviewDto relay,
    List<TicklePreviewDto> tickle
) {

  public static RelayAndTickleDto of(RelayPreviewDto relayPreview,
      List<TicklePreviewDto> ticklePreviews) {
    return new RelayAndTickleDto(relayPreview, ticklePreviews);
  }
}
