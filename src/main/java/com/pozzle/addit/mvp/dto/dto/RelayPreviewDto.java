package com.pozzle.addit.mvp.dto.dto;

import com.pozzle.addit.relay.entity.Relay;
import java.util.List;

public record RelayPreviewDto(
    String relayId,
    String title,
    int totalTickleCount,
    List<String> tags,
    List<String> contributorImages
) {

  public static RelayPreviewDto of(Relay relay, List<String> tags, List<String> contributorImages) {
    return new RelayPreviewDto(
        relay.getUuid(),
        relay.getTitle(),
        relay.getTicklesCount(),
        tags,
        contributorImages
    );
  }
}
