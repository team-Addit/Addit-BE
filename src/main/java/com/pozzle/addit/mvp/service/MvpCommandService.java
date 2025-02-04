package com.pozzle.addit.mvp.service;

import com.pozzle.addit.mvp.dto.request.SessionRequest;
import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.mvp.repository.MvpUserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MvpCommandService {

  private final MvpUserRepository mvpUserRepository;

  public String createUser(SessionRequest sessionRequest) {
    MvpUser user = MvpUser.builder()
        .uuid((UUID.randomUUID().toString()))
        .nickname(sessionRequest.nickname())
        .build();
    mvpUserRepository.save(user);
    return user.getUuid();
  }
}
