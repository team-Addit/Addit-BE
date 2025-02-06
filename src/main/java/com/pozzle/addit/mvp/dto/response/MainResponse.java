package com.pozzle.addit.mvp.dto.response;

import com.pozzle.addit.mvp.dto.dto.RelayAndTickleDto;
import java.util.List;

public record MainResponse(
    List<RelayAndTickleDto> relaysWithTickles
) {

  public static MainResponse of(List<RelayAndTickleDto> bundles) {
    return new MainResponse(bundles);
  }
}
