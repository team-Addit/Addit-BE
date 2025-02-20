package com.pozzle.addit.mvp.repository;

import com.pozzle.addit.mvp.entity.Metric;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {

    Optional<Metric> findByIpAndDate(String ip, LocalDate date);

}
