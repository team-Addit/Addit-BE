package com.pozzle.addit.mvp.dto.dto;

import com.pozzle.addit.relay.entity.Relay;
import java.util.List;

public record RelayPreviewDto(
    String relayId,
    String title,
    int totalTickleCount,
    List<String> tags,
    List<String> userImages
) {

  public static RelayPreviewDto of(Relay r, List<String> tags, List<String> profileImages) {
    return new RelayPreviewDto(
        r.getUuid(),
        r.getTitle(),
        r.getTicklesCount(),
        tags,
        profileImages
    );
  }
}
