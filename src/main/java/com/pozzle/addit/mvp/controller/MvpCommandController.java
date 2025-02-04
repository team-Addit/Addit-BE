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
    HttpSession session = request.getSession(); // 세션이 없으면 생성
    session.setAttribute("username", sessionRequest.nickname()); // 세션 저장
    return Response.ok("success create session", null);
  }

}
