package com.pozzle.addit.relay.repository;

import com.pozzle.addit.relay.entity.RelayTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayTagRepository extends JpaRepository<RelayTag, Long> {

}
