package com.pozzle.addit.relay.repository;

import com.pozzle.addit.relay.entity.RelayTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayTagRepository extends JpaRepository<RelayTag, Long> {

    List<RelayTag> findAllByRelayId(Long relayId);

    void deleteAllByRelayId(Long relayId);
}
