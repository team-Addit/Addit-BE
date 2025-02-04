package com.pozzle.addit.mvp.repository;

import com.pozzle.addit.mvp.entity.MvpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MvpUserRepository extends JpaRepository<MvpUser, Long> {
  Long findIdByNickname(String nickname);
}
