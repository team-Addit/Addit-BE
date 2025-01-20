package com.pozzle.addit.relay.repository;

import com.pozzle.addit.relay.entity.Relay;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayRepository extends JpaRepository<Relay, Long> {

    Optional<Relay> findByUuid(String uuid);
}
