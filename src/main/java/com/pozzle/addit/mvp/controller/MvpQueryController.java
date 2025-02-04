package com.pozzle.addit.mvp.controller;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.mvp.dto.response.SessionResponse;
import com.pozzle.addit.mvp.util.SessionValidator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mvp")
@RequiredArgsConstructor
public class MvpQueryController {

  private final SessionValidator sessionValidator;

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

}
