package com.pozzle.addit.tickle.repository;

import com.pozzle.addit.tickle.entity.Tickle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickleRepository extends JpaRepository<Tickle, Long> {

  Optional<Tickle> findByUuid(String uuid);

  List<Tickle> findAllByRelayId(Long relayId);

  List<Tickle> findTop3ByRelayIdOrderByIdAtAsc(Long relayId);

  List<Tickle> findTop5ByRelayIdOrderByIdAtDesc(Long id);
}
