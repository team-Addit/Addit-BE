package com.pozzle.addit.mvp.service;

import com.pozzle.addit.mvp.entity.Metric;
import com.pozzle.addit.mvp.repository.MetricRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MvpMetricService {

    private final MetricRepository metricRepository;

    public void update(String remoteAddr) {
        Optional<Metric> metric = metricRepository.findByIpAndDate(remoteAddr, LocalDate.now());

        if (metric.isPresent()) {
            metric.get().update();
            log.info("{} : {}", remoteAddr, metric.get().getViewCount());
        } else {
            log.info("New metric created : {}", remoteAddr);
            metricRepository.save(Metric.builder()
                .ip(remoteAddr)
                .build()
            );
        }
    }
}
