package com.pozzle.addit.mvp.dto.dto;

import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.tickle.entity.Tickle;
import java.util.List;

public record RelayPreviewDto(
    String relayId,
    String title,
    int totalTickleCount,
    List<String> tags,
    List<String> userImages,
    String tickleId,
    String userImage,
    String userName
) {

  public static RelayPreviewDto of(Relay relay, List<String> tags, Tickle tickle,
      List<MvpUser> users) {
    return new RelayPreviewDto(
        relay.getUuid(),
        relay.getTitle(),
        relay.getTicklesCount(),
        tags,
        users.stream().map(MvpUser::getImage).toList(),
        tickle.getUuid(),
        users.getFirst().getImage(),
        users.getFirst().getNickname()
    );
  }
}
