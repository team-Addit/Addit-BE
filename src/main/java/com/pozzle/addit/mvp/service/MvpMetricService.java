package com.pozzle.addit.mvp.service;

import com.pozzle.addit.mvp.entity.Metric;
import com.pozzle.addit.mvp.repository.MetricRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MvpMetricService {

    private final MetricRepository metricRepository;

    public void update(String remoteAddr) {
        Optional<Metric> metric = metricRepository.findByIp(remoteAddr);

        if (metric.isPresent()) {
            metric.get().update();
        } else {
            metricRepository.save(Metric.builder()
                .ip(remoteAddr)
                .build()
            );
        }
    }
}
