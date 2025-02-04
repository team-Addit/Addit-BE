package com.pozzle.addit.mvp.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionValidator {

    public String getUsername(HttpServletRequest request) throws RuntimeException {
      HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
      if (session == null) {
        throw new RuntimeException("no session");
      }
      return (String) session.getAttribute("username");
    }

}
