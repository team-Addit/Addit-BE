package com.pozzle.addit.mvp.controller;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.mvp.dto.request.SessionRequest;
import com.pozzle.addit.mvp.service.MvpCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
      HttpServletRequest request,
      @RequestBody SessionRequest sessionRequest
  ) {
    String userId = mvpCommandService.createUser(sessionRequest);
    String nickname = sessionRequest.nickname();

    HttpSession session = request.getSession();
    session.setAttribute("nickname", nickname);
    session.setAttribute("userId", userId);

    return Response.ok("success create session\n"
        + "nickname : " + nickname, null);
  }

}
