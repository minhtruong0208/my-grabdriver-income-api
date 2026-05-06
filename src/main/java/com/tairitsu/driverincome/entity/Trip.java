package com.tairitsu.driverincome.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private BigDecimal netIncome;
    private BigDecimal tip;
    private BigDecimal total;
    private BigDecimal distance;
    @Enumerated(EnumType.STRING)
    private TripType typeOfTrip;
}

