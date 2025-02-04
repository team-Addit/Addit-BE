package com.pozzle.addit.mvp.util;

import com.pozzle.addit.mvp.dto.response.SessionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionValidator {

    public String getUserId(HttpSession session) throws RuntimeException {
      if (session.getAttribute("userId") == null) {
        throw new RuntimeException("no session");
      }
      return (String) session.getAttribute("userId");
    }

  public SessionResponse readSession(HttpServletRequest request) {
    HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
    if (session == null) {
      throw new RuntimeException("no session");
    }

    return new SessionResponse(
        (String) session.getAttribute("nickname"),
        (String) session.getAttribute("userId")
    );
  }
}
