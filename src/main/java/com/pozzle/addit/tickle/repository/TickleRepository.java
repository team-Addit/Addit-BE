package com.pozzle.addit.tickle.repository;

import com.pozzle.addit.tickle.entity.Tickle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickleRepository extends JpaRepository<Tickle, Long> {

}
