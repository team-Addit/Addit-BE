package com.pozzle.addit.mvp.dto.response;

import com.pozzle.addit.mvp.dto.dto.RelayPreviewDto;
import java.util.List;

public record RelayPreviewsResponse(
    List<RelayPreviewDto> relayPreviews
) {

  public static RelayPreviewsResponse of(List<RelayPreviewDto> previews) {
    return new RelayPreviewsResponse(previews);
  }
}
