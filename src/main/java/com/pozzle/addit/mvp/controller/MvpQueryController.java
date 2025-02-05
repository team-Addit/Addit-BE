package com.pozzle.addit.mvp.controller;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.mvp.dto.response.RelayPreviewsResponse;
import com.pozzle.addit.mvp.dto.response.SessionResponse;
import com.pozzle.addit.mvp.dto.response.TicklePreviewsResponse;
import com.pozzle.addit.mvp.dto.response.TickleViewResponse;
import com.pozzle.addit.mvp.service.MvpQueryService;
import com.pozzle.addit.mvp.util.SessionValidator;
import com.pozzle.addit.relay.dto.response.TickleThumbnailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mvp")
@RequiredArgsConstructor
public class MvpQueryController {

  private final SessionValidator sessionValidator;
  private final MvpQueryService mvpQueryService;

  @GetMapping(value = "/session")
  @Operation(summary = "세션 정보 조회",
      description = "세션 정보를 조회합니다."
  )
  public ResponseEntity<?> readSession(
      HttpServletRequest request
  ) {
    SessionResponse sessionResponse;
    try {
      sessionResponse = sessionValidator.readSession(request);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("no session");
    }
    return Response.ok(sessionResponse);
  }

  @GetMapping(value = "/main/relays")
  @Operation(summary = "메인페이지 릴레이 랜덤 조회",
      description = "메인페이지에서 릴레이를 랜덤으로 size개 만큼 조회합니다."
          + "릴레이 썸네일과 간단한 정보를 제공합니다."
  )
  public ResponseEntity<?> readRelayPreviews(
      @RequestParam int size
  ) {
    RelayPreviewsResponse response = mvpQueryService.readRelayPreviews(size);
    return Response.ok(response);
  }

  @GetMapping(value = "/main/{relayId}/tickles")
  @Operation(summary = "릴레이의 최신 티클 미리보기",
      description = "특정 릴레이에 속한 티클의 미리보기를 최신순으로 5개 조회합니다."
  )
  public ResponseEntity<?> readTicklePreviews(
      @PathVariable String relayId
  ) {
    TicklePreviewsResponse response = mvpQueryService.readTicklePreviews(relayId);
    return Response.ok(response);
  }

  @GetMapping(value = "/relays/{relayId}/tickles/thumbnail")
  @Operation(summary = "특정 릴레이의 티클 썸네일 목록",
      description = "릴레이에서 최신순으로 전체 티클들의 썸네일 목록을 불러옵니다."
  )
  public ResponseEntity<?> readTickleThumbnailsFromRelay(
      @PathVariable String relayId
  ) {
    TickleThumbnailsResponse response = mvpQueryService.readTickleThumbnailsFromRelay(relayId);
    return Response.ok(response);
  }

  @GetMapping(value = "/tickles/{tickleId}")
  @Operation(summary = "티클 조회",
      description = "티클을 조회합니다."
  )
  public ResponseEntity<?> readTickle(
      @PathVariable String tickleId
  ) {
    TickleViewResponse response = mvpQueryService.readTickle(tickleId);
    return Response.ok(response);
  }

}
