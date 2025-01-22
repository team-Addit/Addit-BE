package com.pozzle.addit.reply.repository;

import com.pozzle.addit.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    void deleteAllByTickleId(Long tickleId);
}
