package com.pozzle.addit.mvp.dto.response;

import com.pozzle.addit.mvp.dto.dto.TicklePreviewDto;
import java.util.List;

public record TicklePreviewsResponse(
    List<TicklePreviewDto> ticklePreviews
) {

  public static TicklePreviewsResponse of(List<TicklePreviewDto> previews) {
    return new TicklePreviewsResponse(previews);
  }
}
