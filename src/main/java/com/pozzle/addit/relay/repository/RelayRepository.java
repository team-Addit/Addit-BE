package com.pozzle.addit.relay.repository;

import com.pozzle.addit.relay.entity.Relay;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayRepository extends JpaRepository<Relay, Long> {

  Optional<Relay> findByUuid(String uuid);

  @Query(value = "SELECT * FROM recipes ORDER BY RAND() LIMIT :size", nativeQuery = true)
  List<Relay> findRelaysByRandom(@Param("size") int size);
}
