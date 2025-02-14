package com.pozzle.addit.mvp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metrics")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private int viewCount;

    private LocalDateTime inAt;

    private LocalDateTime outAt;

    private LocalDate date;

    @Builder
    public Metric(String ip) {
        this.ip = ip;
        this.viewCount = 1;
        this.inAt = LocalDateTime.now();
        this.outAt = LocalDateTime.now();
        this.date = LocalDate.now();
    }

    public void update() {
        this.outAt = LocalDateTime.now();
        this.viewCount++;
    }
}
