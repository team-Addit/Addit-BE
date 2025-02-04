package com.pozzle.addit.mvp.controller;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.mvp.dto.request.SessionRequest;
import com.pozzle.addit.mvp.service.MvpCommandService;
import com.pozzle.addit.relay.dto.request.RelayCreateRequest;
import com.pozzle.addit.relay.dto.response.RelayCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mvp")
@RequiredArgsConstructor
public class MvpCommandController {

  private final MvpCommandService mvpCommandService;

  @PostMapping(value = "/session")
  @Operation(
      summary = "세션 구축",
      description = "닉네임을 입력하면, 세션을 구축합니다."
  )
  public ResponseEntity<?> createSession(
      HttpSession session,
      @RequestBody SessionRequest sessionRequest
  ) {
    String userId = mvpCommandService.createUser(sessionRequest);
    String nickname = sessionRequest.nickname();

    session.setAttribute("nickname", nickname);
    session.setAttribute("userId", userId);

    return Response.ok("success create session\n"
        + "nickname : " + nickname, null);
  }

  @PostMapping(value = "/relays", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "릴레이 생성",
      description = "릴레이를 생성합니다."
  )
  public ResponseEntity<?> createRelay(
      HttpSession session,
      @RequestPart RelayCreateRequest request,
      @RequestPart MultipartFile file
  ) {
    RelayCreateResponse response = mvpCommandService.createRelay(session, request, file);
    return Response.ok("success create relay", response);
  }

}
